const express = require('express');
const multer = require('multer');
const cors = require('cors');  // Adicione essa linha

const app = express();
const upload = multer();

// Use o middleware CORS para permitir acesso a partir de qualquer origem
    app.use(cors());

app.post('/upload', upload.single('audio'), (req, res) => {
  console.log('Áudio recebido:', req.file);
  res.send('Arquivo recebido com sucesso!');
});

app.listen(3000, () => {
  console.log('Servidor rodando em http://localhost:3000');
});

// const express = require('express');
// const multer = require('multer');
// const cors = require('cors');
// const app = express();
// const upload = multer();

// app.use(cors());

// app.post('/upload', upload.single('audio'), (req, res) => {
//   console.log('Áudio recebido:', req.file);
//   res.send('Arquivo recebido com sucesso!');
// });

// app.listen(3000, () => {
//   console.log('Servidor rodando em http://localhost:3000');
// });
