import { getParamsFromRoute, getRoute } from '../utils';
const defaultState = {
  routeList: [
    {
      name: '机器人列表',
      path: '/mainPage', //定义路由是的path
      routePath: 'mainPage',
      routeParams: [],
      actualPath: '/mainPage', //实际路由的path
    }, 
  ],
  currentRouteKey: '/mainPage', //跟实际路由的path保持一致，含路由参数
};
import Actions from '../actions';
export default function (state = defaultState, action) {
  const type = action.type;
  switch (type) {
    case Actions.NEW_MENU:
      const {
        payload: { path, showName },
      } = action;
      const routeListTemp = state.routeList.slice();
      const idx = routeListTemp.findIndex((item) => item.actualPath == path);
  
      const routeResult = getParamsFromRoute(path);
      let routeParams = getRoute(`${routeResult.routePath}`, path);
      if (idx > -1) {
        //有相同的route
      } else {
        routeParams = {
          ...routeParams,
          showName,
        };
        routeListTemp.push(routeParams);
      }

      //要比较key(这里是path)是否已经在routeList里，如果再不往数组添加，直接改变currentRouteKey即可，如果不在往routeList添加
      return {
        ...state,
        routeList: routeListTemp,
        currentRouteKey: path,
      };
      break;
    case 'clear':
        // console.log("clear ",action);
        // const cb=action?.callback ?? null;
        // console.log('cb is ',cb);
        // cb && cb();
        return {
          ...state,
          ...defaultState,
        }
    break;
    case Actions.DELETE_MENU:
      const routeKey1 = action.payload.routeKey;
      const routeData1 = action.payload.routeData;
      const routeList1 = state.routeList.slice(0);
      const routeResult2 = getParamsFromRoute(routeKey1);
      const routePath2 = routeResult2.routePath;
      const idx1 = routeList1.findIndex((item) => item.path == routeKey1);
      routeList1.splice(idx1, 1);
      let curRouteKey;
      if (routeList1.length > 0) {
        const newIdx = idx1 - 1;
        const item = routeList1[newIdx];
        curRouteKey = item.actualPath;
      } else {
        curRouteKey='/mainPage',
        routeList1.push({
          name: '机器人列表',
          path: '/mainPage', //定义路由是的path
          routePath: 'mainPage',
          routeParams: [],
          actualPath: '/mainPage', //实际路由的path
        });
      }
      return {
        ...state,
        routeList: routeList1,
        currentRouteKey: curRouteKey,
      };
      break;
    case Actions.CHANGE_MENU:
      const {
        payload: { routeKey, routeData, callback },
      } = action;
      const routeResult1 = getParamsFromRoute(routeKey);
      return {
        ...state,
        currentRouteKey: routeKey,
      };
      break;
    default:
      return state;
      break;
  }
}
