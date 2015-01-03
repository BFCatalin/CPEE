using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using CPEE.Extensions;
using System.Runtime.Serialization;

namespace CPEE.Models
{
    public class Price : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        private int _Id;
        private string _Name;
        private decimal _IT;
        private decimal _MT;
        private decimal _JT;
        private decimal _TL;
        private int _countyCount;

        public Price() { }

        public Price(int id, string name, decimal IT, decimal MT, decimal JT, decimal TL)
            : this()
        {
            this._Id = id;
            this._Name = name;
            this._IT = IT;
            this._MT = MT;
            this._JT = JT;
            this._TL = TL;
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

        public decimal IT
        {
            get { return _IT; }
            set { PropertyChanged.ChangeAndNotify(ref _IT, value, () => IT); }
        }

        public decimal MT
        {
            get { return _MT; }
            set { PropertyChanged.ChangeAndNotify(ref _MT, value, () => MT); }
        }

        public decimal JT
        {
            get { return _JT; }
            set { PropertyChanged.ChangeAndNotify(ref _JT, value, () => JT); }
        }

        public decimal TL
        {
            get { return _TL; }
            set { PropertyChanged.ChangeAndNotify(ref _TL, value, () => TL); }
        }

        [IgnoreDataMember]
        public int CountyCount
        {
            get { return _countyCount; }
            set { PropertyChanged.ChangeAndNotify(ref _countyCount, value, () => CountyCount); }
        }
    }
}
