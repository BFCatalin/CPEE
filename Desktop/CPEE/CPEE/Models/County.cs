using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

using CPEE.Extensions;

namespace CPEE.Models
{
    public class County : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        private int _Id;
        private string _Name;
        private Price _Price;
        private int _priceId;

        public County() { }

        public County(int id, string name, Price price)
            : this()
        {
            this._Id = id;
            this._Name = name;
            this._Price = price;
        }

        public int Id
        {
            get { return _Id; }
            set { PropertyChanged.ChangeAndNotify(ref _Id, value, () => Id); }
        }

        public string Name
        {
            get { return _Name; }
            set { PropertyChanged.ChangeAndNotify(ref _Name, value, () => Name); }
        }

        public int PriceId
        {
            get { return _priceId; }
            set { PropertyChanged.ChangeAndNotify(ref _priceId, value, () => PriceId); }
        }

        [IgnoreDataMember]
        public Price Price
        {
            get { return _Price; }
            set
            {
                PropertyChanged.ChangeAndNotify(ref _Price, value, () => Price);
                if (value != null)
                    _priceId = value.Id;
            }
        }
    }
}
