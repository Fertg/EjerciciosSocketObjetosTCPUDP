# EjerciciosSocketObjetosTCPUDP
Ejercicios de procesos y servicios sobre sockets TCP/UDP



EJERCICIOS CON LAS CLASES SOCKET
1. Realiza un programa servidor TCP que acepte dos clientes. Muestra por cada cliente conectado sus puertos local y remoto.
Crea también el programa cliente que se conecte a ese servidor. Muestra los puertos locales y remotos a los que está conectado su socket, y a la dirección IP de la máquina remota a la que se conecta.

2. Crea un programa servidor TCP que envíe un mensaje a otro programa cliente y el programa cliente que le devuelva el mensaje en minúscula.

3. Crea un programa cliente que introduzca teclado un número entero y se lo envíe al servidor. El servidor le devolverá el cuadrado del número. Hacerlo con socket TCP.

4. Crea un programa servidor TCP que pueda atender hasta 3 clientes. Debe enviar a cada cliente un mensaje indicando el número de cliente que es. Este número será 1, 2 o 3. 
   El cliente mostrará el mensaje recibió. Cambia el programa para que lo haga para N cliente, siendo N un parámetro que tendrás que definir en el programa.

5. Crea un programa UDP cliente que envíe un texto tecleado en su entrada estándar al servidor (en un puerto pactado), el servidor UDP lee el datagrama y devuelve el número de vocales que hay en el texto. El programa cliente recibe el datagrama del servidor y muestra el número de vocales que hay en el texto.

6. Crea una clase Java llamada Numeros que defina 3 atributos, uno de ellos entero, y los otros dos de tipo long:
int numero;
long cuadrado;
long cubo;
Define un constructor con parámetros y otro sin parámetros. Define los métodos get y set de los atributos. Crea un programa cliente que introduzca por teclado un número e inicialice un objeto Numeros, el atributo numero debe contener el número introducido por teclado. Debe enviar ese objeto al programa servidor. El proceso se repetirá mientras el número introducido por teclado sea mayor que 0.
Crea un programa servidor que reciba un objeto Numeros. Debe calcular el cuadrado y el cubo del atributo numero y debe enviar el objeto al cliente con los cálculos realizados, el cuadrado y el cubo en sus atributos respectivos. El cliente recibirá el objeto y visualizará el cuadrado y el cubo del número introducido por teclado. El programa servidor finalizara cuando el número recibido en el objeto Numeros sea menor o igual que 0.
Controla posibles errores, por ejemplo si ejecutamos el cliente y el servidor no está inicializado, o si estando el servidor ejecutándose ocurre algún error en el cliente, o este finaliza inesperadamente.


7. Usando socket UDP. Realiza un programa servidor que espere un datagrama de un cliente. El cliente le envía un objeto Persona que previamente había inicializado. El servidor modifica los datos del objeto Persona y se lo envía de vuelta al cliente. Visualiza los datos del objeto Persona tanto en el programa cliente cuando los envía y los recibe como en el programa servidor cuando los recibe y los envía modificados.
