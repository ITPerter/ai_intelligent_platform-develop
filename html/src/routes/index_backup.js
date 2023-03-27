import {
    Link,
    Switch,
    HashRouter as Router,
    Route,
    Redirect,
    withRouter,
  } from 'react-router-dom';
  import React, { Suspense } from 'react';
  import Header from '../components/Header';
  import SlideNav from '../components/SlideNav';
  import MainPage from '../pages/mainPage';
  const NotMatch = React.lazy(() => import('../pages/notMatch/404.js'));
  const IntentionList = React.lazy(() => import('../pages/Intention/index.js'));
  const IntentionDetail = React.lazy(() => import('../pages/IntentionDetail'));
  const RobotDetail = React.lazy(() => import('../pages/RobotDetail'));
  import NavBar from '../components/NavBar';
  import './index.less';
  function HasError() {
    return <div>出错啦</div>;
  }
  import CommonSer from '../images/commonSer.png';
  import CreateTime from '../images/createtime.png';
  import routeList from '../common/route';
  const navData = [
    {
      id: 1,
      img: CommonSer,
      name: '机器人',
      children: [
        {
          id: 1,
          showNav: true,
          path: '/mainPage',
          name: '机器人列表',
        },
      ],
    },
    {
      id: 2,
      name: '意图',
      img: CreateTime,
      children: [
        {
          id: 1,
          showNav: true,
          path: '/intentionList',
          name: '意图列表',
        },
      ],
    },
  ];
  export default class App extends React.Component {
    constructor(props) {
      super(props);
      this.handleSlideNavItemClick = this.handleSlideNavItemClick.bind(this);
    }
  
    handleLogout() {}
    handleSlideNavItemClick(moduleId, menuItem) {
      history.push(menuItem.path);
    }
    getRouteArr(sourceList, targetList = []) {
      const _this = this;
      sourceList.forEach((item) => {
        targetList.push(
          <Route
            key={item.path}
            path={item.path}
            component={item.component}
            exact={item.exact}
          ></Route>
        );
        if (item.children) {
          _this.getRouteArr(item.children, targetList);
        }
      });
    }
    renderRoute(arr = []) {
      let routeArr = [];
      this.getRouteArr(routeList, routeArr);
      return <Switch>
        {
           routeArr
        }
        <Redirect from="/" to="/mainPage"/>
      </Switch>;
    }
    render() {
      return (
        <div className="router-wrapper">
          <Router>
            <Header />
            <div className="bodyContent">
              <div className="slideNav">
                <SlideNav
                  data={navData}
                  onMenuItemClick={this.handleSlideNavItemClick}
                />
              </div>
              <div className="content">
                <NavBar />
                <Suspense fallback={<HasError />}>
                  <div className="content-page">{this.renderRoute()}</div>
                </Suspense>
              </div>
            </div>
          </Router>
        </div>
      );
    }
  }
  