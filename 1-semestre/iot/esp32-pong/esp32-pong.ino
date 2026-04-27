#include <WiFi.h>

const char* ssid = "BERNARDINI";
const char* password = "Italia2023@";

void setup() {
  Serial.begin(115200);
  delay(1000);

  Serial.println("INICIANDO WIFI");

  WiFi.begin(ssid, password);

  int tentativas = 0;

  while (WiFi.status() != WL_CONNECTED && tentativas < 20) {
    delay(500);
    Serial.println("Tentando conectar...");
    tentativas++;
  }

  if (WiFi.status() == WL_CONNECTED) {
    Serial.println("CONECTOU");
    Serial.println(WiFi.localIP());
  } else {
    Serial.println("FALHOU WIFI");
  }
}

void loop() {
}