using CollectionsAndExceptions.Entities;
using CollectionsAndExceptions.Exception;
using System;
using System.Collections.Generic;
using System.Text;

namespace CollectionsAndExceptions.Services
{
    public class FrotaService
    {
        private Dictionary<string, Carro> _garagem = new Dictionary<string, Carro>();

        public void CadastrarCarro(Carro carro)
        {
            if(_garagem.ContainsKey(carro.Placa))
            {
                throw new AluguelException("Este carro já está cadastrado no sistema.");
            }
            _garagem.Add(carro.Placa, carro);
        }

        public void AlugarCarro(string placa)
        {
            if (!_garagem.ContainsKey(placa))
            {
                throw new VeiculoNaoEncontradoException($"Carro da placa {placa} não encontrado no sistema.");
            }

            _garagem[placa].RealizarAlguel();
        }
    }   


}
