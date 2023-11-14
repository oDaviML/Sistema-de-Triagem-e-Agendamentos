const express = require('express')
const nodemailer = require('nodemailer')
const app = express()
const port = 3000

app.use(express.json());

const transporter = nodemailer.createTransport({
    service : "gmail",
    secure : "false",
    auth: {
        user: "quickcheck380@gmail.com",
        pass: "etcvankoiwbgnhmi"
    },
    tls: {
        rejectUnauthorized: false
    }
})

app.post('/enviaremail', (req, res) =>{
    const { email, subject, html } = req.body;

    const mailSent = transporter.sendMail({
        from: "quickcheck380@gmail.com",
        to: email,
        subject: subject,
        html: html
    }).then(() => {
        res.status(200).send(req.body);
        console.log('enviado');
    }).catch((err) => {
        console.log(err);
        res.send(err);
    });
})

app.listen(port, () =>{
    console.log(`Servidor de email on: ${port}`)
})