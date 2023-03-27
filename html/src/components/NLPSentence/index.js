import React from 'react';
import './index.less';
import { connect } from 'react-redux';
import NLPWordslot from '../NLPWordslot';
import NLPFrame from '../NLPFrame';
import actions from '../../actions';
import { message } from 'antd';
const prefixCls = 'web-nlpsentence';
class NLPSentence extends React.Component {
  constructor(props) {
    super(props);
  }
  state = {
    sampleEntities: [], //在本地维持一个样本数据
    showFrame: false, //是否显示词槽面板
    wordslotList: [],
    offset: 0,
    usedWordslotArr: [],
  };
  componentWillReceiveProps(nextProps) {
    console.log('component will receive props!!');
    // const {sampleItemList, content, intentId} = nextProps;
    // if (content !== this.props.content
    //     || intentId !== this.props.intentId
    //     ) {
    this.createNewSampleEntities(nextProps);
    //}
  }
  componentDidMount() {
    this.createNewSampleEntities(this.props);
  }
  /**
   * 重构词槽数组
   */
  createNewSampleEntities = (props = this.props) => {
    let { sampleItemList, content, wordslotList } = props;
    console.log('createNew sampleEntities !!!');
    if(!sampleItemList)sampleItemList=[];
    console.log("sampleItemList in createNewSampleEntities is ",sampleItemList);
    let usedWordslotArr = [];
    if(sampleItemList){
      sampleItemList.sort(function (a, b) {
        return a.start - b.start;
      });
      sampleItemList.forEach((item) => {
        if (item.hasOwnProperty('slotNumber')) {
          usedWordslotArr.push(item.slotNumber);
        }
      });
    }



    let arr = [];
    let lastItem = null;
    if (sampleItemList && sampleItemList.length == 0) {
      for (let i = 0, len = content.length; i < len; i++) {
        arr.push({ value: content[i], start: i, end: i + 1 });
      }
    }

    for (let i = 0, len = sampleItemList.length; i < len; i++) {
      let item = sampleItemList[i];
      if (i == 0) {
        if (item.start != 0) {
          //arr.push({ start: 0, end: item.start, value: sentence.substring(0, item.start) })
          let tempArr = this.splitSentence(content, 0, item.start);
          arr.push(...tempArr);
        }
        arr.push(item);
      }
      if (lastItem) {
        if (lastItem.end != item.start) {
          let tempArr = this.splitSentence(content, lastItem.end, item.start);
          arr.push(...tempArr);
        }
        arr.push(item);
      }
      if (i == len - 1) {
        if (item.end != content.length) {
          let tempArr = this.splitSentence(content, item.end, content.length);
          arr.push(...tempArr);
        }
      }
      lastItem = item;
    }
    console.log('before arr is ', JSON.stringify(arr));
    arr.forEach((item, index) => {
      item.keyId = index;
      item.status = item.slotNumber ? 'selected' : 'canSelect';
    });
    console.log('arr after is ', arr);
    this.setState({
      sampleEntities: arr,
      usedWordslotArr,
    });
  };
  splitSentence = (sentence, start, end) => {
    let arr = [];
    for (let i = start; i < end; i++) {
      arr.push({ start: i, end: 1 + i, value: sentence.substring(i, 1 + i) });
    }
    return arr;
  };
  getStyle = (target) => {
    const rect = target.getBoundingClientRect();
    const parent = target.parentNode;
    const parentRect = parent && parent.getBoundingClientRect();
    return rect.left - parentRect.left;
  };
  getNewSampleEntity = (start, end) => {
    const { sampleEntities } = this.state;
    const tempArr = sampleEntities.slice(start, end + 1);
    let newObj = {};
    if (tempArr && tempArr.length == 2) {
      const obj1 = tempArr[0],
        obj2 = tempArr[1];
      newObj.start = obj1.start;
      newObj.end = obj2.end;
      newObj.value = obj1.value + obj2.value;
      newObj.status = 'selecting';
      newObj.keyId = start;
    }
    return newObj;
  };
  /**
   * 保存样本标注
   */
  handleSaveSampleEntity = (obj,oldSelectedWs) => {
    const { dispatch } = this.props;
    const sampleEntities = this.state.sampleEntities.slice(0);
    const usedWordslotArr = this.state.usedWordslotArr.slice(0);
    const _this = this;
    const { slotNumber } = obj;
    if(oldSelectedWs){
      const idx=usedWordslotArr.findIndex(item=>item==oldSelectedWs);
      if(idx > -1){
        usedWordslotArr.splice(idx,1);
      }
    }
    if (usedWordslotArr && usedWordslotArr.includes(slotNumber)) {
    } else {
      usedWordslotArr.push(slotNumber);
    }

    dispatch({
      type: actions.ADD_SAMPLE_ENTITY,
      payload: obj,
      callback: function (result) {
        const { code, msg } = result;
        if (code == 0) {
          _this.isSelectingObj = null;
          _this.setState({
            showFrame: false,
            sampleEntities: _this.resetEntityStatus(obj),
            usedWordslotArr,
          });
        } else {
          message.error(msg);
        }
      },
    });
  };
  resetEntityStatus = (obj) => {
    let sampleEntities = this.state.sampleEntities.slice();
    sampleEntities.forEach((item) => {
      if (item.start === obj.start && item.end === obj.end) {
        item.id = obj.id;
      }
      item.status =
        item.status === 'selecting'
          ? 'selected'
          : item.status == 'noSelect'
          ? 'canSelect'
          : item.status;
    });
    return sampleEntities;
  };
  //每次点击要重新拼装sampleEntities数组
  changeSampleEntities = (itemData) => {
    let sampleEntities = this.state.sampleEntities.slice();
    let preIdx = -1,
      nextIdx = -1;
    const id = itemData && itemData.keyId;
    let newSampleEntities = null;
    if (itemData.status == 'selected' || itemData.status == 'selecting')
      return sampleEntities;
    let idx = -1;
    let selectedIdx = itemData.keyId;
    if (this.isSelectingObj != null) {
      const lastIdx = this.isSelectingObj.selectedIdx;
      const curIdx = itemData.keyId;
      let tempArr = null;
      let newIdx = 0;
      let newObj = null;
      if (lastIdx < curIdx) {
        newIdx = selectedIdx = lastIdx;
        newObj = this.getNewSampleEntity(lastIdx, curIdx);
      } else {
        newIdx = selectedIdx = curIdx;
        newObj = this.getNewSampleEntity(curIdx, lastIdx);
      }
      sampleEntities.forEach((item) => {
        if (item.keyId > selectedIdx) {
          item.keyId--;
        }
      });
      sampleEntities.splice(newIdx, 2, newObj);
    }
    this.itemData = sampleEntities[selectedIdx];

    preIdx = selectedIdx - 1;
    nextIdx = 1 + selectedIdx;

    sampleEntities.forEach((item) => {
      if (item.keyId == selectedIdx) item.status = 'selecting';
      else if (
        item.status != 'selected' &&
        item.status != 'selecting' &&
        ((item.keyId == preIdx && preIdx >= 0) ||
          (item.keyId == nextIdx && nextIdx < sampleEntities.length))
      ) {
        item.status = 'canSelect';
      } else if (item.status != 'selected') {
        item.status = 'noSelect';
      }
    });

    this.isSelectingObj = {
      selectedIdx: selectedIdx,
    };
    return sampleEntities;
  };
  handleTagClick = (e, item) => {
    const target = e.target;
    const _this = this;
    this.itemData = item;
    const offset = this.getStyle(target);
    if (this.isSelectingObj) {
      if (offset < this.offsetLeft) {
        this.offsetLeft = offset;
      }
    } else {
      this.offsetLeft = offset;
    }
    const sampleEntities = this.changeSampleEntities(item);
    this.setState({
      showFrame: true,
      selectedWs: item.slotNumber,
      offset: this.offsetLeft,
      sampleEntities,
    });
  };
  renderSampleEntities() {
    const { sampleEntities } = this.state;
    console.log('sampleEntities in renderSampleEnities  is ', sampleEntities);
    const str = sampleEntities.map((item, index) => {
      return (
        <NLPWordslot
          key={item.keyId}
          item={item}
          onClick={this.handleTagClick}
        ></NLPWordslot>
      );
    });
    return <div className={`${prefixCls}-sentenceRow`}>{str}</div>;
  }
  splitWordslot = (data) => {
    let sampleEntities = this.state.sampleEntities.slice();
    const idx = this.getIndexFromArr(data);
    let newIdx = idx;
    let newArr = [],
      index = idx;
    const value = data.value;
    let wordStart = data.start;
    for (let i = 0, len = value.length; i < len; i++, index++) {
      const obj = {};
      obj.start = wordStart + i;
      obj.end = 1 + obj.start;
      obj.keyId = index;
      obj.value = value[i];
      obj.status = 'canSelect';
      newArr.push(obj);
    }
    newIdx += value.length;

    sampleEntities.forEach((item) => {
      if (item.status != 'selected') item.status = 'canSelect';
      if (item.keyId > idx) {
        item.keyId = newIdx++;
      }
    });
    sampleEntities.splice(idx, 1, ...newArr);
    this.isSelectingObj = null;
    this.setState({
      sampleEntities,
      showFrame: false,
    });
  };
  /**
   * 请求删除标注的词槽
   */
  removeSampleEntities = (sampleId, cb) => {
    const { dispatch } = this.props;
    dispatch({
      type: actions.REMOVE_SAMPLE_ENTITY,
      payload: {
        id: sampleId,
      },
      callback: function (result) {
        console.log('removeSampleEntity result is ', result);
        const { code, msg } = result;
        if (code != 0) {
          message.error(msg);
        } else {
          cb && cb();
        }
      },
    });
  };
  //移除
  removeWordslot = (data) => {
    console.log('data removeWordslot is ', JSON.stringify(data));

    this.removeSampleEntities(data.id, () => this.removeResetEntities(data));
  };
  /**
   * 后台删除词槽标注后页面重构sampleEntities
   */
  removeResetEntities = (data) => {
    const { slotNumber } = data;
    let usedWordslotArr = this.state.usedWordslotArr.slice();
    if (usedWordslotArr && usedWordslotArr.includes(slotNumber)) {
      const idx = usedWordslotArr.findIndex((item) => item == slotNumber);
      if (idx > -1) {
        usedWordslotArr.splice(idx, 1);
      }
    }
    let sampleEntities = this.state.sampleEntities.slice();
    let idx = -1;
    sampleEntities.forEach((item, index) => {
      if (item.start == data.start && item.end == data.end) {
        idx = index;
        return;
      }
    });
    let tempArr = [];
    let start = data.start;
    const val = data.value;
    for (let i = 0, len = val.length; i < len; i++) {
      tempArr.push({
        start,
        end: 1 + start,
        value: val[i],
        sampleId: data.sampleId,
        status: 'canSelect',
      });
      start = 1 + start;
    }
    sampleEntities.splice(idx, 1, ...tempArr);
    sampleEntities.forEach((item, index) => {
      item.keyId = index;
    });
    this.setState({
      sampleEntities,
      showFrame: false,
      usedWordslotArr,
    });
  };
  handleFrameAction = (actionName, data) => {
    if (actionName == '切词') {
      this.splitWordslot(data);
    } else if (actionName == '移除') {
      this.removeWordslot(data);
    }
  };
  //选定词槽，向服务器提交修改
  /**
   * 选定词槽，向服务器提交修改
   * item:词槽
   * data:样本片段
   * selectedWs:如果是重新选择词槽，将之前的词槽从usedWordslotArr干掉
   */
  handleWordslotSelect = (item, data,selectedWs) => {
    if (data.value.length > 40) {
      message.error('标注词槽不能超过40个字!');
      return false;
    }
    const { onSaveSample, sampleId } = this.props;
    const sampleEntities = this.state.sampleEntities.slice();
    data['wordslotId'] = item.id;
    data['slotNumber'] = item.number;
    data['sampleId'] = sampleId;
    this.handleSaveSampleEntity(data,selectedWs);
  };
  handleFrameClose = () => {
    const _this = this;
    this.isSelectingObj = null;
    const sampleEntities = this.resetSelectingWord();
    this.setState({
      showFrame: false,
      sampleEntities,
    });
  };
  resetSelectingWord = () => {
    const sampleEntities = this.state.sampleEntities.slice();
    let temp = sampleEntities.filter((item) => item.status === 'selecting');
    if (temp.length < 1) return sampleEntities;
    temp = temp[0];
    let tempArr = [];
    let keyId = temp.keyId;
    let idx = keyId;
    let start = temp.start;
    let textValue = temp.value;
    for (let i = 0, len = textValue.length; i < len; i++) {
      tempArr.push({ keyId, start, end: 1 + start, value: textValue[i] });
      keyId++;
      start++;
    }
    sampleEntities.splice(idx, 1, ...tempArr);
    sampleEntities.forEach((item, index) => {
      item.keyId = index;
      item.status = item.status != 'selected' ? 'canSelect' : item.status;
    });
    return sampleEntities;
  };
  render() {
    const { content, wordslotList } = this.props;
    const { showFrame, selectedWs, offset, usedWordslotArr } = this.state;
    return (
      <div>
        {this.renderSampleEntities()}
        <NLPFrame
          wordslotList={wordslotList}
          onAction={this.handleFrameAction}
          onChooseWordSlot={this.handleWordslotSelect}
          visible={showFrame}
          data={this.itemData}
          selectedWs={selectedWs}
          style={{ left: offset, top: '100%' }}
          onFrameClose={this.handleFrameClose}
          usedWordslotArr={usedWordslotArr}
        />
      </div>
    );
  }
}

export default connect((state) => {
  return {
    wordslotList: state.wordslot.list,
  };
})(NLPSentence);
