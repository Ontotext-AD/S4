using S4ClientApi.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ontotext.S4.ontotext.service
{
    /// <summary>
    ///  Object-oriented representation of the classification information for a document as returned by the S4 online API
    /// </summary>
    public class ClassifiedDocument
    {
        private String Category;
        private List<ClassificationCategory> AllScores;

        /// <summary>
        /// The most probable category (as specified on docs.s4.ontotext.com)
        /// For more information, please visit
        /// http://docs.s4.ontotext.com/display/S4docs/News+Classifier#NewsClassifier-DescriptionofCategories
        /// </summary>
        public String category
        {
            get { return Category; }
            set { Category = value; }
        }

        /// <summary>
        /// List of the 3 most probable categories to which the document belongs, as well as their probability score
        /// Note: This includes the Category property which will always have the highest score
        /// </summary>
        public List<ClassificationCategory> allScores
        {
            get { return AllScores; }
            set { AllScores = value; }
        }
    }
}
