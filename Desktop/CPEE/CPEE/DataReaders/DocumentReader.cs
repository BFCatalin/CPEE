using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.Serialization.Json;
using System.IO;

using CPEE.Extensions;
using CPEE.Models;

namespace CPEE.DataReaders
{
    public class DocumentReader
    {
        public CPEEDocument ReadEmptyDocument()
        {
            CPEEDocument doc = new CPEEDocument();
            return doc;
        }

        public CPEEDocument ReadDocument(string filePath)
        {
            CPEEDocument document = null;
            DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(CPEEDocument));
            using (var fileStream = File.Open(filePath, FileMode.Open, FileAccess.Read))
            {
                document = serializer.ReadObject(fileStream) as CPEEDocument;
            }
            if (document.Counties == null)
                document.Counties = new ObservableCollectionEx<County>();
            if (document.Prices == null)
                document.Prices = new ObservableCollectionEx<Price>();

            document.Refresh();
            document.IsDirty = false;
            return document;
        }
    }
}
