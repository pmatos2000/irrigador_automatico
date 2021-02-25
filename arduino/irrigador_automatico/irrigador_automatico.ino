#include <SD.h>
#define ENTRADA_SENSOR A0 //Pino usado para a leitura do sensor
#define VALOR_SOLO_MOLHADO 768
#define VALOR_SOLO_SECO 256

#define ACIONAMENTO_MOTOR 9

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
void setup() {
  Serial.begin(9600);
  pinMode(ACIONAMENTO_MOTOR, OUTPUT);
}

void loop() {
  delay(1000);
  modificaAcionamentoMotor(!motorLigado);
}
