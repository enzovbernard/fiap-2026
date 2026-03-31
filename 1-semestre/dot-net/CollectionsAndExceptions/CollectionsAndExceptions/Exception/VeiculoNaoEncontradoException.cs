using System;
using System.Collections.Generic;
using System.Text;

namespace CollectionsAndExceptions.Exception
{
    public class VeiculoNaoEncontradoException : System.Exception
    {
        public VeiculoNaoEncontradoException(string message) : base(message) { }
    }

    public class AluguelException : System.Exception
    {
        public AluguelException(string message) : base(message) { }
    }
}
