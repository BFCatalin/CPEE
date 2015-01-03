using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using CPEE.Extensions;

namespace CPEE.Models
{
    public class GlobalPrices : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        private decimal _CoGen;
        private decimal _TG;
        private decimal _TL;
        private decimal _Service;

        public GlobalPrices() { }

        public GlobalPrices(decimal coGen, decimal tg, decimal tl, decimal service)
            : this()
        {
            this._CoGen = coGen;
            this._TG = tg;
            this._TL = tl;
            this._Service = service;
        }

        [Description("Contributie cogenerare")]
        public decimal CoGen
        {
            get { return _CoGen; }
            set { PropertyChanged.ChangeAndNotify(ref _CoGen, value, () => CoGen); }
        }

        [Description("TG")]
        public decimal TG
        {
            get { return _TG; }
            set { PropertyChanged.ChangeAndNotify(ref _TG, value, () => TG); }
        }

        [Description("TL")]
        public decimal TL
        {
            get { return _TL; }
            set { PropertyChanged.ChangeAndNotify(ref _TL, value, () => TL); }
        }

        [Description("Service")]
        public decimal Service
        {
            get { return _Service; }
            set { PropertyChanged.ChangeAndNotify(ref _Service, value, () => Service); }
        }
    }
}
