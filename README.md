#JSON-DATABASE#

###Descri��o###
Esta aplicação consiste em um MVC robusto criado para que pedaços de código relevantes sejam armazenados em banco de dados persistente.
A API opera com CRUD, sendo possível criar novos códigos, e consultá-los. A atualização e remoção dos códigos está implementada, mas funciona apenas de maneira automática por ora.
Para alguns códigos, há um tempo de vida e um limite de views.



###RELEASE 0.0.1###
Implementação do View via Mustache (futuramente será alterado para Angular);
/api/code/latest -> mostra os últimos 10 códigos postados em formato JSON;
/api/code/UUID -> mostra o código com id UUID em formato JSON;
/code/latest -> mostra o frontend para os últimos 10 códigos postados;
/code/UUID -> mostra o código com id UUID no frontend;
/code/new -> formulário para postar um novo código.