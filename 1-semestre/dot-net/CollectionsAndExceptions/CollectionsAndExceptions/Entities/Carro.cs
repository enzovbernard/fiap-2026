using CollectionsAndExceptions.Exception;
using System;
using System.Collections.Generic;
using System.Text;

namespace CollectionsAndExceptions.Entities
{
    public class Carro : Veiculo
    {
        public Carro(string placa, string modelo) : base(placa, modelo)
        {
        }

        public override void RealizarAlguel()
        {
            if (EstaAlugado)
            {
                throw new AluguelException($"O carro do modelo {Modelo} e placa {Placa} já está alugado.");
            }
            EstaAlugado = true;
        }
    }
}
