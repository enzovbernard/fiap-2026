using System;
using System.Collections;
using System.Linq.Expressions;
using CollectionsAndExceptions.Entities;
using CollectionsAndExceptions.Exception;

namespace CollectionsAndExceptions
{
    internal class Program
    {
        static void Main(string[] args)
        {
            var frota = new Services.FrotaService();

            frota.CadastrarCarro(new Carro("ABC-1234", "Toyota Corolla"));
            frota.CadastrarCarro(new Carro("XYZ-7890", "GM Cruze"));

            Console.WriteLine("=== Sistema de Aluguel de Carros ===\n");

            try
            {
                Console.WriteLine("Tentando alugar o Corolla...");
                frota.AlugarCarro("ABC-1234");
                Console.WriteLine("Aluguel confirmado com sucesso!\n");

                //Console.WriteLine("Tentando alugar o Corolla de novo");
                //frota.AlugarCarro("ABC-1234");

                Console.WriteLine("Tentando alugar um carro que não existe na frota...");
                frota.AlugarCarro("QWE-5678");
            }
            catch (AluguelException ex)
            {
                Console.WriteLine($"AVISO: {ex.Message}");
            }
            catch (VeiculoNaoEncontradoException ex)
            {
                Console.WriteLine($"ERRO DE BUSCA: {ex.Message}");
            }
            catch (System.Exception ex)
            {
                Console.WriteLine($"ERRO CRÍTICO: {ex.Message}");
            }

            Console.WriteLine("\n=== Fim do Sistema ===");  


        }
    }
}