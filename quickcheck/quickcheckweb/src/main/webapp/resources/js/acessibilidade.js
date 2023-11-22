new window.VLibras.Widget('https://vlibras.gov.br/app');

const openButton = document.querySelector('#btn-verde');
const reviewPopup = document.querySelector('#review-popup');
const fecharMenu = document.querySelector('#fecharMenu');

openButton.addEventListener('click', () => {
    if (reviewPopup.classList.contains('hidden')) {
        reviewPopup.classList.remove('hidden');
    } else {
        reviewPopup.addEventListener('transitionend', () => {
            reviewPopup.classList.add('hidden');
        }, { once: true });
    }
});

fecharMenu.addEventListener('click', () => {
    reviewPopup.classList.add('hidden');
})

const card = document.querySelector('.card');

// Aumentar texto

const btnTextoMaior = document.querySelector('#textoMaiorBtn');
let textoStatus = 0;

btnTextoMaior.addEventListener('click', () => {
    const elementos = document.querySelectorAll('h1, h2, h3, h4, p, th, td, label');
    textoStatus++;

    if (textoStatus === 1) {
        btnTextoMaior.classList.add('cardAtivo');
        elementos.forEach((elemento) => {
            const tamanhoAtual = parseFloat(window.getComputedStyle(elemento).fontSize);
            elemento.style.fontSize = tamanhoAtual * 1.2 + 'px';
        });
    }
    else if (textoStatus === 2) {
        elementos.forEach((elemento) => {
            const tamanhoAtual = parseFloat(window.getComputedStyle(elemento).fontSize);
            elemento.style.fontSize = tamanhoAtual * 1.3 + 'px';
        });
    }
    else {
        textoStatus = 0;
        btnTextoMaior.classList.remove('cardAtivo');
        elementos.forEach((elemento) => {
            const tamanhoAtual = parseFloat(window.getComputedStyle(elemento).fontSize);
            elemento.style.fontSize = tamanhoAtual / 1.56 + 'px';
        });
    }
})

// Cursor customizado

const btnCursor = document.querySelector('#cursorMaiorBtn');
const cursor = document.querySelector('#cursor');
let isCursorMaior = false;

btnCursor.addEventListener('click', () => {
    btnCursor.classList.toggle('cardAtivo');

    isCursorMaior = !isCursorMaior;

    if (isCursorMaior) {
        document.body.addEventListener('mousemove', mouseMoveHandlerCursor);
        cursor.style.display = 'block';
    } else {
        document.body.removeEventListener('mousemove', mouseMoveHandlerCursor);
        cursor.style.display = 'none';
    }
});

const mouseMoveHandlerCursor = (event) => {
    const cursor = document.getElementById('cursor');
    const x = event.clientX - cursor.offsetWidth / 2;
    const y = event.clientY - cursor.offsetHeight / 2;

    cursor.style.left = `${x}px`;
    cursor.style.top = `${y}px`;
};


// Contraste

const btnContraste = document.querySelector('#contrasteBtn');
let isContrasteAtivo = false;

btnContraste.addEventListener('click', () => {
    btnContraste.classList.toggle('cardAtivo');
    isContrasteAtivo = !isContrasteAtivo;

    if (isContrasteAtivo) {
        document.documentElement.style.filter = 'invert(100%)';
    } else {
        document.documentElement.style.filter = 'none';
    }
});

// Saturação

const btnSaturacao = document.querySelector('#saturacaoBtn');
let saturacaoStatus = 0;

btnSaturacao.addEventListener('click', () => {
    saturacaoStatus++;

    if (saturacaoStatus === 1) {
        document.documentElement.style.filter = 'saturate(200%)';
        btnSaturacao.classList.add('cardAtivo');
    }
    else if (saturacaoStatus === 2) {
        document.documentElement.style.filter = 'saturate(30%)';
    }
    else if (saturacaoStatus === 3) {
        document.documentElement.style.filter = 'saturate(0%)';
    } else {
        saturacaoStatus = 0;
        document.documentElement.style.filter = 'saturate(100%)';
        btnSaturacao.classList.remove('cardAtivo');
    }
});


// Barra informação

const barraInformacoes = document.querySelector('#barraInformacoes');
const btnBarra = document.querySelector('#infoBtn');
const elementsToTrack = ['button', 'h1', 'h2', 'h3', 'p', 'a', 'span', 'td', 'th', 'label'];
let barraAtiva = false;

const mouseOverHandler = (e) => {
    const element = e.target;
    const content = element.textContent;

    if (content && elementsToTrack.includes(element.tagName.toLowerCase())) {
        barraInformacoes.textContent = content;
        barraInformacoes.style.display = 'block';
    }
};

const mouseOutHandler = () => {
    barraInformacoes.style.display = 'none';
};

const mouseMoveHandler = (e) => {
    const mouseX = e.clientX;
    const mouseY = e.clientY + 10;
    if (isCursorMaior) {
        barraInformacoes.style.left = (mouseX + 10) + 'px';
        barraInformacoes.style.top = (mouseY + 10) + 'px';

    } else {
        barraInformacoes.style.left = mouseX + 'px';
        barraInformacoes.style.top = mouseY + 'px';
    }
};

btnBarra.addEventListener('click', () => {
    btnBarra.classList.toggle('cardAtivo');
    barraAtiva = !barraAtiva;

    if (barraAtiva) {
        document.addEventListener('mouseover', mouseOverHandler);
        document.addEventListener('mouseout', mouseOutHandler);
        document.addEventListener('mousemove', mouseMoveHandler);
    } else {
        document.removeEventListener('mouseover', mouseOverHandler);
        document.removeEventListener('mouseout', mouseOutHandler);
        document.removeEventListener('mousemove', mouseMoveHandler);
        barraInformacoes.style.display = 'none';
    }
});


// Text to Speach

const btnTexto = document.querySelector('#voiceBtn');
let isVoiceAtivo = false;
let timeoutId;

btnTexto.addEventListener('click', () => {
    btnTexto.classList.toggle('cardAtivo');
    isVoiceAtivo = !isVoiceAtivo;

    if (isVoiceAtivo) {
        document.body.addEventListener('mousemove', mouseMoveHandlerVoz);
    } else {
        document.body.removeEventListener('mousemove', mouseMoveHandlerVoz);
        clearTimeout(timeoutId);
    }
})

const mouseMoveHandlerVoz = (e) => {
    clearTimeout(timeoutId);

    timeoutId = setTimeout(() => {
        const element = e.target;
        const content = element.textContent;

        if (content && elementsToTrack.includes(element.tagName.toLowerCase())) {
            const utterance = new SpeechSynthesisUtterance(content);
            utterance.rate = 1.5;
            utterance.pitch = 1;
            window.speechSynthesis.speak(utterance);
        }
    }, 1000);
}