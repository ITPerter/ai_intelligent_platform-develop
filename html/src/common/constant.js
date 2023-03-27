

//机器人训练状态
export const trainState={
    UN_TRAIN:{
        number:0,
        name:'未训练'
    },
    TRAIN_SUCCESS:{
        number:1,
        name:'已训练',
    },
    TRAIN_ERROR:{
       number:2,
       name:'训练失败',
    },
    TRAINING:{
        number:3,
        name:'训练中',
    }
}

export function getTrainStateText(stateNum){
    let statusText="";
    switch (stateNum) {
        case trainState.UN_TRAIN.number:
          statusText = trainState.UN_TRAIN.name;
          break;
        case trainState.TRAIN_SUCCESS.number:
          statusText = trainState.TRAIN_SUCCESS.name;
          break;
        case trainState.TRAIN_ERROR.number:
          statusText = trainState.TRAIN_ERROR.name;
          break;
        case trainState.TRAINING.number:
          statusText = trainState.TRAINING.name;
          break;
      }
      return statusText;

}