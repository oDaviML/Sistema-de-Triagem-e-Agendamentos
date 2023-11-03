/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

// Microfone

const EFEITOS_SONOROS = [
    'comeca-escutar',
    'conseguiu-escutar',
    'cancelou-escuta'].reduce((prev, cur) => {
    prev[cur] = new Audio(`audio/${cur}.mp3`);
    return prev;
}, {});

const COMANDOS_DE_VOZ = {
    cadastroMedico: ['cadastrar', 'cadastrarmedico'],
    acessarInicio: ['voltar', 'incio', 'acessarInicio']
};

const GRAMATICA = `#JSGF V1.0; grammar comando; public <comando> = ${Object.values(COMANDOS_DE_VOZ).reduce((prev, cur) => prev.concat(cur, [])).join(' | ')} ;`;

const inicializaReconhecimentoDeFala = (callback, microfoneEl) => {
    let prefix = ['', 'webkit', 'moz'];
    for (let p of prefix) {
        if (`${p}SpeechRecognition` in window) {
            prefix = p;
            break;
        }
    }

    if (!Array.isArray(prefix)) {
        let reconhecimento = new window[`${prefix}SpeechRecognition`]();
        let palavrasParaReconhecimento = new window[`${prefix}SpeechGrammarList`]();
        palavrasParaReconhecimento.addFromString(GRAMATICA, 1);
        reconhecimento.grammars = palavrasParaReconhecimento;
        reconhecimento.lang = 'pt-BR';
        reconhecimento.continuous = false;
        reconhecimento.interimResults = false;
        reconhecimento.maxAlternatives = 1;
        reconhecimento.start();
        reconhecimento.onresult = (e) => {
            let ultima = e.results.length - 1;
            let comandos = e.results[ultima][0].transcript.trim().toLowerCase().split(' ');

            // cria um balão de texto mostrando o que foi falado/reconhecido ou não
            let balaoComando = document.createElement('output');
            balaoComando.classList.add('balao-comando');
            balaoComando.addEventListener('animationend', (e) => {
                document.body.removeChild(balaoComando)
            });
            let conteudoBalaoComado = [];

            // para cada palavra falada nesta frase...
            for (let comando of comandos) {
                let acaoDesteComando = null;
                for (let c of Object.keys(COMANDOS_DE_VOZ)) {

                    if (COMANDOS_DE_VOZ[c].indexOf(comando) !== -1) {
                        acaoDesteComando = callback[c];
                        // esta palavra foi reconhecida como um comando válido
                        conteudoBalaoComado.push(comando);
                        break;
                    } else {
                        conteudoBalaoComado;
                    }
                }

                if (acaoDesteComando !== null) {
                    // esta palavra (comando) foi reconhecida
                    acaoDesteComando();
                } else {
                    // esta palavra (comando) não foi reconhecida
                    conteudoBalaoComado.push(`<span class="desconhecido">${comando}</span>`);
                }
            }

            // define o conteúdo do balão com os comandos
            balaoComando.innerHTML = conteudoBalaoComado.join(' ');
            document.body.appendChild(balaoComando);

            // som de sucesso      
            EFEITOS_SONOROS['conseguiu-escutar'].play();
        };

        reconhecimento.onend = () => {
            // pára a animação de escuta do microfone
            microfoneEl.classList.remove('listening');
            document.body.querySelector('#instrucoes').classList.remove('ativa');
        };

        reconhecimento.onerror = (e) => {
            reconhecimento.onend();
            EFEITOS_SONOROS['cancelou-escuta'].play();
        };

        reconhecimento.onnomatch = reconhecimento.onerror;


        return reconhecimento;
    }
};

const INSTRUCOES_ELS = [
    'Inicio',
    'Cadastro Medico'
].map(i => {
    let instrucaoEl = document.createElement('span');
    instrucaoEl.className = 'instrucao';
    instrucaoEl.innerText = i;
    return instrucaoEl;
});

const inicializaInstrucoes = () => {
    let instrucoesEl = document.createElement('aside');
    instrucoesEl.id = 'instrucoes';
    INSTRUCOES_ELS.forEach(el => instrucoesEl.appendChild(el));
    return instrucoesEl;
};

const inicializaMicrofone = () => {
    let botaoMicEl = document.querySelector("#mic");

    let instrucoesEl = inicializaInstrucoes();
    document.body.appendChild(instrucoesEl);
    botaoMicEl.addEventListener('mouseover', e => instrucoesEl.classList.add('ativa'));
    botaoMicEl.addEventListener('mouseleave', e => instrucoesEl.classList.remove('ativa'));
};

class Bando {
    constructor() {
        this.ovelhitas = [];
        inicializaMicrofone();
        let microfoneEl = document.querySelector('#mic');
        microfoneEl.addEventListener('click', e => {
            const animatedEl = microfoneEl.firstElementChild;

            if (animatedEl.classList.contains('listening')) {
                this.reconhecimento.stop();
                document.body.querySelector('#instrucoes').classList.remove('ativa');
                animatedEl.classList.remove('listening');
                EFEITOS_SONOROS['cancelou-escuta'].play();
            } else {
                document.body.querySelector('#instrucoes').classList.add('ativa');
                animatedEl.classList.add('listening');
                EFEITOS_SONOROS['comeca-escutar'].play();
                this.reconhecimento = inicializaReconhecimentoDeFala({
                    cadastroMedico: this.cadastro.bind(this),
                    acessarInicio: this.inicio.bind(this)
                }, animatedEl);
            }
        });
    }
    
    cadastro() {
        cadastrarMedico();
    }
    
    inicio() {
        window.location.href = "lgnMedico.xhtml";
    }
}

new Bando().nova();