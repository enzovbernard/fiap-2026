using System;
using System.Collections.Generic;
using System.Text;

namespace CollectionsAndExceptions.Entities
{
    public abstract class Veiculo
    {
        public string Placa { get; set; }

        public string Modelo { get; set; }

        public bool EstaAlugado { get; protected set; }

        public Veiculo(string placa, string modelo)
        {
            Placa = placa;
            Modelo = modelo;
            EstaAlugado = false;
        }

        public abstract void RealizarAlguel();

    }
}
