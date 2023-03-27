import React, { Suspense } from 'react';
import './index.less';
import {
  Switch,
  HashRouter,
  Route,
  Redirect,
  withRouter,
} from 'react-router-dom';
import MainPage from '../../pages/mainPage';
import { connect } from 'react-redux';
import Header from '../../components/Header';
import SlideNav from '../../components/SlideNav';
const NotMatch = React.lazy(() => import('../../pages/notMatch/404.js'));
const IntentionList = React.lazy(() =>
  import('../../pages/Intention/index.js')
);
const IntentionDetail = React.lazy(() => import('../../pages/IntentionDetail'));
const RobotDetail = React.lazy(() => import('../../pages/RobotDetail'));

import NavBar from '../../components/NavBar';
import Actions from '../../actions';
import { message } from 'antd';
import './index.less';
function HasError() {
  return <div>出错啦</div>;
}
import CommonSer from '../../images/commonSer.png';
import CreateTime from '../../images/createtime.png';
import routeList from '../../common/route';


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
  {
    id: 3,
    name: '基础资料',
    img: CreateTime,
    children: [
      {
        id: 1,
        showNav: true,
        path: '/baseData',
        name: '基础资料列表',
      },
    ],
  },
  {
    id: 4,
    name: '任务技能列表',
    img: CreateTime,
    children: [
      {
        id: 1,
        showNav: true,
        path: '/taskSkillList',
        name: '任务型技能详情',
      },
    ],
  },

];
class BasicLayout extends React.Component {
  constructor(props) {
    super(props);
    this.handleSlideNavItemClick = this.handleSlideNavItemClick.bind(this);
    this.handleLogout = this.handleLogout.bind(this);
  }
  componentWillReceiveProps(nextProps) {
    const { pathname } = nextProps.location;
  }
  componentDidMount() {
    this.props.history.listen((location) => {
      console.log('basic layout did mount location is ', location);
      console.log(
        'this.props.location.pathname is ',
        this.props.location.pathname
      );
    });
  }

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
    return (
      <Switch>
        {routeArr}
        <Redirect from="/" to="/mainPage" />
      </Switch>
    );
  }

  handleLogout() {
    const _this = this;
    const { dispatch, history } = this.props;

    dispatch({
      type: Actions.LOGOUT,
      payload: {},
      callback(result) {
        const { code, msg } = result;
        if (code != 0) {
          message.error(msg);
        } else {
          dispatch({
            type:'clear',
            payload:{
              'test':2
            },
          })  
          history.push('/user');
        }
      },
    });
  }

  handleMenuTabClick = (key, item) => {
    const { dispatch, history } = this.props;
    dispatch({
      type: Actions.CHANGE_MENU,
      payload: {
        routeKey: key,
        routeData: item,
      },
    });
    history.push(item.actualPath);
  };
  handleMenuTabDel = (key, item) => {
    const { dispatch } = this.props;
    dispatch({
      type: Actions.DELETE_MENU,
      payload: {
        routeKey: key,
        routeData: item,
      },
    });
  };
  
  render() {
    return (
      <div className="router-wrapper">
        <Header onLogout={this.handleLogout} />
        <div className="bodyContent">
          <div className="slideNav">
            <SlideNav data={navData} onMenuItemClick={this.handleSlideNavItemClick}/>
          </div>
          <div className="content">
            <NavBar history={this.props.history} onMenuTabClick={this.handleMenuTabClick} onMenuDel={this.handleMenuTabDel}/>
            <Suspense fallback={<HasError />}>
              <div className="content-page">{this.renderRoute()}</div>
            </Suspense>
          </div>
        </div>
      </div>
    );
  }
}

export default withRouter(connect()(BasicLayout));

/**
 *             <Switch>
                <Route exact path="/mainPage" component={MainPage}>

                </Route>
            </Switch>
 */
