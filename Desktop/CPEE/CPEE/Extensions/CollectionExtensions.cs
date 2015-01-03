using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CPEE.Extensions
{
    public static class CollectionExtensions
    {
        public static void ForEach<T>(this ICollection<T> collection, Action<T> action)
        {
            foreach (T item in collection)
            {
                action(item);
            }
        }
    }
}
