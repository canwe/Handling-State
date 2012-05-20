package model.bags;

import model.xml.State;
import model.xml.StateChart;

/// <summary>
    /// Bag class for state building
    /// </summary>
    public class StateBag {
        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="s">state</param>
        /// <param name="c">statechart</param>
        public StateBag(State s, StateChart c) {
            state = s;
            parentChart = c;
        }

        private State state;
        private StateChart parentChart;

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }

        public StateChart getParentChart() {
            return parentChart;
        }

        public void setParentChart(StateChart parentChart) {
            this.parentChart = parentChart;
        }
    }
