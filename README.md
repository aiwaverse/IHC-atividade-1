# IHC - Trabalho Prático 1
Esse trabalho foi desenvolvimento em Android, usando [Jetpack Compose](https://developer.android.com/jetpack/compose), o toolkit recomendado pelo Google para desenvolvimento moderno em Android em Kotlin, usando a versão da API do Android Nougat (7), que tem mais de 95% de cobertura.

O desenvolvimento com o Compose difere do apresentado nos tutoriais, porém é consideravelmente intuitivo, e comparável com React com o conceito de estado e mutabilidade.

Os arquivos com o código final estão sempre na pasta *app/src/main/java/com/ihc/[nome do projeto]/MainActivity.kt*, alguns exercícios possuem um arquivo a mais (explicado abaixo).
## Parte 1
A parte 1, descrita [nesse pdf](https://moodle.inf.ufrgs.br/pluginfile.php/75752/mod_resource/content/3/Atividade%20Pr%C3%A1tica%20Android.pdf), está implementada em 3 projetos separados, um para cada exercício, nas pastas [TP1Parte1Exercicio1](https://github.com/aiwaverse/IHC-trabalho-pratico-1/tree/main/TP1Parte1Exercicio1 "TP1Parte1Exercicio1"),  [TP1Parte1Exercicio2](https://github.com/aiwaverse/IHC-trabalho-pratico-1/tree/main/TP1Parte1Exercicio2 "TP1Parte1Exercicio2") e [TP1Parte1Exercicio3](https://github.com/aiwaverse/IHC-trabalho-pratico-1/tree/main/TP1Parte1Exercicio3 "TP1Parte1Exercicio3").
### Exercicio 1
Basta usar os inputs de texto e o botão "SUM" para somar os dois valores, mostrando o resultado abaixo do botão. São aceitos números decimais quaisquer, caso os números sejam inválidos (ex: em branco), é mostrado uma string vazia no lugar.
### Exercício 2
Em vez de Activities para navegação, a solução do Compose é usar [navigation](https://developer.android.com/jetpack/compose/navigation), isso é feito usando os componentes de navController e NavHost, cada tela é definida em um Composable (nesse caso, MessageDisplay e MessageSetUpAndConfirmButton), e callbacks são usados para configurar quando deve haver a navegação entre as telas (no caso, ao apertar o botão).
### Exercício 3
Usando um SensorManager, definimos um sensor e o listener para o acelerômetro, quando os valores nas coordenadas x ou y passam de 10, o programa exibe a mensagem. Para abstrair um pouco, a classe [Accelerometer](https://github.com/aiwaverse/IHC-trabalho-pratico-1/blob/main/TP1Parte1Exercicio3/app/src/main/java/com/ihc/tp1parte1exercicio3/Accelerometer.kt), que implementa a interface SensorEventListener é usada.
## Parte 2
A parte 2 foi feita em um projeto só [TP1Parte2Exercicios123](https://github.com/aiwaverse/IHC-trabalho-pratico-1/tree/main/TP1Parte2Exercicios123 "TP1Parte2Exercicios123"), visto que os 3 TextFields conseguem ocupar a mesma tela.
São mostrados os sensores de luminosidade e pressão (com atualização em tempo real) na tela, e também as coordenadas de latitude e longitude (atualizadas a cada 1 segundo +-). Para o GPS, precisamos das permissões de localização, sem elas, o aplicativo não exibe esses campos, ficando somente com luminosidade e pressão.
Os Sensores foram abstraidos na classe [GenericSensor](https://github.com/aiwaverse/IHC-trabalho-pratico-1/blob/main/TP1Parte2Exercicios123/app/src/main/java/com/ihc/tp1parte2exercicios123/GenericSensor.kt), que novamente implementa SensorEventListener, porém agora de uma maneira mais genérica se comparada a parte 1.
Para as permissões, usamos o ActivityResultContracts, a função rememberLauncherForActivityResult e o conceito de side-effects do Compose em LauncherEffect.
Para o GPS, é usado a solução padrão do Android atual, o FusedLocationProviderClient, registrando um pedido de atualização continua da localização com um callback das variáveis de estado.



