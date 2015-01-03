﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;

namespace CPEE.Extensions
{
    public sealed class PropertyDescriptionConverter : IValueConverter
    {
        public object Convert(object value, Type targetType, object parameter, System.Globalization.CultureInfo culture)
        {
            if (value == null)
                return Binding.DoNothing;
            
            string propertyName = parameter as string;
            if (String.IsNullOrEmpty(propertyName))
                return new ArgumentNullException("parameter").ToString();
            
            Type type = value.GetType();
            PropertyInfo property = null;
            if (propertyName.Contains("."))
            {
                string[] propertyComponents = propertyName.Split(new char[] { '.' });
                property = type.GetProperty(propertyComponents[0], BindingFlags.Public | BindingFlags.Instance);
                if (property == null)
                    return new ArgumentOutOfRangeException("parameter", parameter,
                        "Property \"" + propertyName + "\" not found in type \"" + type.Name + "\".").ToString();
                object propValue = property.GetValue(value);
                propertyComponents = propertyComponents.Skip(1).ToArray();
                return Convert(propValue, targetType, String.Join(".", propertyComponents), culture);
            }
            property = type.GetProperty(propertyName, BindingFlags.Public | BindingFlags.Instance);
            if (property == null)
                return new ArgumentOutOfRangeException("parameter", parameter,
                    "Property \"" + propertyName + "\" not found in type \"" + type.Name + "\".").ToString();

            if (!property.IsDefined(typeof(DescriptionAttribute), true))
                return new ArgumentOutOfRangeException("parameter", parameter,
                    "Property \"" + propertyName + "\" of type \"" + type.Name + "\"" +
                    " has no associated Description attribute.").ToString();

            return ((DescriptionAttribute)property.GetCustomAttributes(typeof(DescriptionAttribute), true)[0]).Description;
        }

        public object ConvertBack(object value, Type targetType, object parameter, System.Globalization.CultureInfo culture)
        {
            throw new NotSupportedException();
        }
    }
}

