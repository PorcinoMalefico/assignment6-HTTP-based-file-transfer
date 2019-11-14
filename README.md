# HTTP-based file transfer
Scrivere un programma JAVA che implementi un server HTTP che gestisca richieste di trasferimento di file di diverso tipo (es. immagini jpeg, gif e/o testo) provenienti da un browser web.

Il server sta in ascolto su una porta nota al client (es. 6789)
Gestisce richieste HTTP di tipo GET inviate da un browser alla URL localhost:port/filename
Le connessioni possono essere non persistenti.

Usare le classi Socket e ServerSocket per sviluppare il programma server.
Per inviare al server le richieste, utilizzare un qualsiasi browser.
