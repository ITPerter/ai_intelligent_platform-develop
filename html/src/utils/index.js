import routeList from '../common/route';
export function isEmpty(target) {
  const isEmptyRegExp = /^\s*$/;
  return isEmptyRegExp.test(target);
}

// 判断下拉是否选中
export function isSelected(id) {
  var obj = document.getElementById(id);
  // console.log("被选中用选项索引"+obj.options.selectedIndex); 
  var index = obj.options.selectedIndex;
  // var text1 = obj.options[index].text;
  // console.log(text1); 文本
  // console.log(obj.options.length); 选项个数 4
  // console.log(text1.length===0);
  // console.log('结果是:')
  // console.log(Number(index)===0);

  if (Number(index)===0){
    return true;
  }
  
}


function setStorageItem(key, value) {
  sessionStorage.setItem(key, JSON.stringify(value));
}
function getStorageItem(key) {
  const storageItem = window.sessionStorage.getItem(key);
  return storageItem;
}

export function getRoute(routePath, actualPath) {
  //    console.log('getRoute routePath is ',routePath);
  let routeVal = getStorageItem('routeList');
  if (!routeVal) {
    routeVal = [];
    getRouteListFromRoute(routeList, routeVal);
    setStorageItem('routeList', routeVal);
  } else {
    routeVal = JSON.parse(routeVal);
  }
  //    console.log("getRoute routeVal data is ",JSON.stringify(routeVal));
  let result = routeVal.filter((item) => item.routePath == routePath);
  if (result.length > 0) {
    result = result[0];
    result['actualPath'] = actualPath;
  }
  return result;
}

function getRouteListFromRoute(sourceList, targetArr = []) {
  sourceList.forEach((item) => {
    const { name, path } = item;
    const routePars = getParamsFromRoute(path);
    targetArr.push({
      name,
      path,
      ...routePars,
    });
    if (item.children) {
      getRouteListFromRoute(item.children, targetArr);
    }
  });
}

export function getParamsFromRoute(path) {
  const reg = /^\/(\w+)(\/\:(\w+))*/;
  const result = reg.exec(path);
  console.log('result is ', result);
  let routePath = '';
  let params = [];
  if (result.length > 0) {
    routePath = result[1];
    if (result[2]) {
      params.push(result[3]);
    }
  }
  return {
    routePath,
    routeParams: params,
  };
}

//获取数组的最大ID+1 返回
export function FilterMaxId(list, columnName) {
  if (!list) return 1;
  list.sort(function (a, b) {
    return a[columnName] - b[columnName];
  });
  const temp = list && list[list.length - 1];
  if (temp == undefined) return 1;
  if (columnName in temp) return parseInt(temp[columnName]) + 1;
  return 1;
}
