#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

#define LARGURA 128
#define ALTURA 64

Adafruit_SSD1306 display(LARGURA, ALTURA, &Wire, -1);

#define BOTAO_SUBIR 4  
#define BOTAO_DESCER 5 

#define BUZZER 18

int raqueteY = 22;
const int raqueteAltura = 15;
const int raqueteX = 5;
const int raqueteLargura = 4;

int bolaX = LARGURA/2;
int bolaY = ALTURA/2;
int bolaDirX = 2;
int bolaDirY = 2;
const int bolaTamanho = 4;

int pontuacao = 0;
int vidas = 3;

bool gameOver = false;

void setup() {
  Wire.begin(21, 22);
  display.begin(SSD1306_SWITCHCAPVCC, 0x3C);
  display.clearDisplay();

  pinMode(BOTAO_SUBIR, INPUT_PULLUP);
  pinMode(BOTAO_DESCER, INPUT_PULLUP);
  pinMode(BUZZER, OUTPUT);
}

void loop() {
  if(!gameOver){
    if(digitalRead(BOTAO_SUBIR) == LOW) {
      raqueteY -= 3;
      if(raqueteY < 0) raqueteY = 0;
    }
    if(digitalRead(BOTAO_DESCER) == LOW) {
      raqueteY += 3;
      if(raqueteY > ALTURA - raqueteAltura) raqueteY = ALTURA - raqueteAltura;
    }

    bolaX += bolaDirX;
    bolaY += bolaDirY;

    if(bolaY <= 0 || bolaY >= ALTURA - bolaTamanho) {
      bolaDirY = -bolaDirY;
      tone(BUZZER, 1000, 50);
    }

    if(bolaX <= raqueteX + raqueteLargura && bolaX + bolaTamanho >= raqueteX) {
      if(bolaY + bolaTamanho >= raqueteY && bolaY <= raqueteY + raqueteAltura) {
        bolaDirX = -bolaDirX;
        tone(BUZZER, 1500, 50);
        pontuacao++;
      }
    }

    if(bolaX >= LARGURA - bolaTamanho) {
      bolaDirX = -bolaDirX;
      tone(BUZZER, 1200, 50);
    }

    if(bolaX < 0) {
      vidas--;
      tone(BUZZER, 400, 300);

      for (int i = 0; i < 5; i++) {
        display.clearDisplay();
        int cx = LARGURA/2;
        int cy = ALTURA/2;
        for(int j=0;j<8;j++){
          int dx = random(-8,9);
          int dy = random(-8,9);
          display.drawPixel(cx+dx, cy+dy, WHITE);
        }
        display.display();
        delay(100);
      }

      bolaX = LARGURA/2;
      bolaY = ALTURA/2;
      bolaDirX = 2;
      bolaDirY = 2;

      if(vidas <= 0){
        gameOver = true;
        delay(1500); 
      }
    }

    display.clearDisplay();
    display.fillRect(raqueteX, raqueteY, raqueteLargura, raqueteAltura, WHITE);
    display.fillRect(bolaX, bolaY, bolaTamanho, bolaTamanho, WHITE);

    display.setTextSize(2);
    display.setTextColor(WHITE);
    int digitos = String(pontuacao).length();
    int xPos = LARGURA - 6*2*digitos;
    display.setCursor(xPos, 0);
    display.print(pontuacao);

    for(int i=0;i<vidas;i++){
      display.fillCircle(LARGURA - 5 - i*6, ALTURA - 5, 2, WHITE);
    }

    display.display();
    delay(20);

  } else {
    display.clearDisplay();
    display.setTextSize(1);
    display.setTextColor(WHITE);
    display.setCursor(30, 20);
    display.print("VOCE PERDEU :(");
    display.setCursor(20, 35);
    display.print("Pontuacao final: ");
    display.print(pontuacao);

    int cx = LARGURA/2;
    int cy = ALTURA/2;
    for(int j=0;j<12;j++){
      int dx = random(-10,11);
      int dy = random(-10,11);
      display.drawPixel(cx+dx, cy+dy, WHITE);
    }
    display.display();
    tone(BUZZER, 500, 200);

    while(true){
      if(digitalRead(BOTAO_SUBIR) == LOW || digitalRead(BOTAO_DESCER) == LOW){
        pontuacao = 0;
        vidas = 3;
        raqueteY = 22;
        bolaX = LARGURA/2;
        bolaY = ALTURA/2;
        bolaDirX = 2;
        bolaDirY = 2;
        gameOver = false;
        delay(300); 
        break;
      }
    }
  }
}