import React from 'react';
import BasicLayout from '../layouts/BasicLayout';
import UserLayout from '../layouts/UserLayout';
import './index.less';
import { Switch, HashRouter as Router, Route } from 'react-router-dom';

class APP extends React.Component {
  constructor(props) {
    super(props);
  }
  
  render() {
    return (
      <div className="router-wrapper">
        <Router>
          <Switch>
            <Route exact path="/user" children={({ match }) => <UserLayout match={match} />}></Route>
            <Route path="/"><BasicLayout /></Route>
          </Switch>
        </Router>
      </div>
    );
  }
}

export default APP;
