package model.bags;

import model.xml.Transition;

/// <summary>
    /// Bag class for transition building
    /// </summary>
    public class TransitionBag {
        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="t">transition</param>
        /// <param name="s">state bag</param>
        public TransitionBag(Transition t, StateBag s) {
            transition = t;
            stateBag = s;
        }

        private Transition transition;
        private StateBag stateBag;

        public Transition getTransition() {
            return transition;
        }

        public void setTransition(Transition transition) {
            this.transition = transition;
        }

        public StateBag getStateBag() {
            return stateBag;
        }

        public void setStateBag(StateBag stateBag) {
            this.stateBag = stateBag;
        }
    }