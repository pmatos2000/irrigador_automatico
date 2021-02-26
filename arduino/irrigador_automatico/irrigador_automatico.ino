#include <SD.h>
#include <SPI.h>
#define ENTRADA_SENSOR A0 //Pino usado para a leitura do sensor
#define VALOR_SOLO_MOLHADO 768
#define VALOR_SOLO_SECO 256

#define ACIONAMENTO_MOTOR 9

File myFile;

float valorSensor = 0;
bool motorLigado = false;

namespace TipoDeSolo{
  enum{
    MOLHADO,
    INDEFINIDO,
    SECO,
  };
}

int verificaSolo(){
  if(valorSensor > VALOR_SOLO_MOLHADO) return TipoDeSolo::MOLHADO;
  if(valorSensor < VALOR_SOLO_SECO) return TipoDeSolo::SECO;
  return TipoDeSolo::INDEFINIDO;
}

void realizaLeituraSensor(){
  float valor = 0;
  //Realiza um filtro de media 
  for(int i = 0; i < 100; i++){
    valor += analogRead(ENTRADA_SENSOR);
    delay(1);
  }
  valorSensor = valor/100.0;
}

void escrevaSaidaSeria(){
  Serial.print(valorSensor);
  switch(verificaSolo()){
    case TipoDeSolo::MOLHADO:
      Serial.println(" - solo molhado");
      break;
    case TipoDeSolo::SECO:
      Serial.println(" - solo seco");
      break;
    default:
      Serial.println(" - indefinido");
      break;
  }
}

void modificaAcionamentoMotor(bool valor){
  motorLigado = valor;
  digitalWrite(ACIONAMENTO_MOTOR, valor ? HIGH : LOW);
}


void inicializaConfig(){
  Serial.print("Initializing SD card...");
  if (!SD.begin(4)) {
    Serial.println("initialization failed!");
    while (1);
  }
  Serial.println("initialization done.");
  // open the file. note that only one file can be open at a time,
  // so you have to close this one before opening another.
  // if the file opened okay, write to it:
  if (SD.exists("config.txt")) {
    Serial.print("Arquivo de configuracao encontrado.");
  } else {
    // if the file didn't open, print an error:
    Serial.println("Arquivo de configuracao nao encontrado.");
    Serial.println("Criando arquivo de configuração");
    myFile = SD.open("config.txt", FILE_WRITE);
    myFile.println("400");
    // close the file:
    myFile.close();
    Serial.println("done.");
  }  
  // Open the file for reading:
  myFile = SD.open("config.txt");
  if (myFile) {
    Serial.println("config.txt:");

    // read from the file until there's nothing else in it:
    while (myFile.available()) {
      Serial.write(myFile.read());
    }
    // close the file:
    myFile.close();
  }
}


void setup() {
  Serial.begin(9600);
  inicializaConfig();
  pinMode(ACIONAMENTO_MOTOR, OUTPUT);
}
void loop() {
  delay(1000);
  modificaAcionamentoMotor(!motorLigado);
}
