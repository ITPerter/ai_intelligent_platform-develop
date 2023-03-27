
// 机器人列表
export const chatbotColumns = [
  {
    title: '名称',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    key: 'createTime',
  },
  {
    title: '描述',
    dataIndex: 'des',
    key: 'des',
  },
  {
    title: '创建人',
    dataIndex: 'creator',
    key: 'creator',
  },
  {
    title: '训练状态',
    dataIndex: 'trainState',
    key: 'trainState',
  },
  {
    title: '操作',
    dataIndex: 'action',
    key: 'action',
  },
];

export const getChatBotColumns = (renderObj) => {
  for (let key in renderObj) {
    chatbotColumns.forEach((item) => {
      if (item.key == key) {
        item.render = (text, record) => renderObj[key](text, record);
        item.renderFn = true;
      }
    });
  }
  return chatbotColumns;
};

// 意图
const intentionListColumns = [
  {
    title: '名称',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: '编码',
    dataIndex: 'number',
    key: 'number',
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    key: 'createTime',
  },
  {
    title: '描述',
    dataIndex: 'des',
    key: 'des',
  },
  {
    title: '操作',
    dataIndex: 'action',
    key: 'action',
  },
];

export const getIntentionColumns = (renderObj) => {
  for (let key in renderObj) {
    intentionListColumns.forEach((item) => {
      if (item.key == key) {
        item.render = (text, record) => renderObj[key](text, record);
        item.renderFn = true;
      }
    });
  }
  return intentionListColumns;
};



const wordslotColumns = [
  {
    title: '名称',
    key: 'name',
    dataIndex: 'name',
  },
  {
    title: '编码',
    key: 'number',
    dataIndex: 'number',
  },
  {
    title: '澄清术语',
    key: 'clarification',
    dataIndex: 'clarification',
  },{
    title:'是否必录',
    key:'must',
    dataIndex:'must',
  },{
    title:'类型',
    key:'type',
    dataIndex:'type',
  },
  {
    title: '描述',
    key: 'des',
    dataIndex: 'des',
  },
  {
    title: '操作',
    key: 'action',
    dataIndex: 'action',
  },
];
export const getWordslotColumns = (renderObj) => {
  for (let key in renderObj) {
    wordslotColumns.forEach((item) => {
      if (item.key == key) {
        item.render = (text, record) => renderObj[key](text, record);
        item.renderFn = true;
      }
    });
  }
  return wordslotColumns;
};

const sampleColumns = [
  {
    title: '样本标注',
    key: 'name',
    dataIndex: 'name',
  },
  {
    title: '状态',
    key: 'intentTrain',
    dataIndex: 'intentTrain',
  },
  {
    title: '操作',
    key: 'action',
    dataIndex: 'action',
  },
];
export const getSampleColumns = (renderObj) => {
  for (let key in renderObj) {
    sampleColumns.forEach((item) => {
      if (item.key == key) {
        item.render = (text, record) => renderObj[key](text, record);
        item.renderFn = true;
      }
    });
  }
  return sampleColumns;
};

export const robotIntentionColumns = [
  {
    title: '意图名称',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: '意图编码',
    dataIndex: 'number',
    key: 'number',
  },
];

// 机器人技能添加菜单列表
export const robotSkillColumns = [
  {
    title: '技能名称',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: '技能编码',
    dataIndex: 'number',
    key: 'number',
  },
  {
    title: '技能描述',
    dataIndex: 'des',
    key: 'des',
  },
  {
    title: '技能类型',
    dataIndex: 'type',
    key: 'type',
  },
  {
    title: '所属产品线',
    dataIndex: 'productLine',
    key: 'productLine',
  }
]


// 基本资料
 const baseDataTypeColumns=[
  {
    title:'名称',
    dataIndex:'name',
    key:'name',
  },
  {
    title:'编码',
    dataIndex:'number',
    key:'number',
  },{
    title:'描述',
    dataIndex:'des',
    key:'des'
  },
  {
    title:'操作',
    dataIndex:'action',
    key:'action'
  }
]

export function getBaseDataTypeColumns(renderObj){
  for (let key in renderObj) {
    baseDataTypeColumns.forEach((item) => {
      if (item.key == key) {
        item.render = (text, record) => renderObj[key](text, record);
        item.renderFn = true;
      }
    });
  }
  return baseDataTypeColumns;
}

const baseDataValueColumns=[
  {
    title:'名称',
    dataIndex:'value',
    key:'value',
  },
  {
    title:'编码',
    dataIndex:'number',
    key:'number',
  },
  {
    title:'操作',
    dataIndex:'action',
    key:'action'
  }
]
export function getBaseDataValueColumns(renderObj){
  for (let key in renderObj) {
    baseDataValueColumns.forEach((item) => {
      if (item.key == key) {
        item.render = (text, record) => renderObj[key](text, record);
        item.renderFn = true;
      }
    });
  }
  return baseDataValueColumns;
}

// TaskSkillList 任务技能列表
const taskSkillListColumns = [
  {
    title:'技能名称',
    dataIndex:'name',
    key:'name'    
  },
  {
    title:'技能编码',
    dataIndex:'number',
    key:'number'  
  },
  {
    title:'产品线',
    dataIndex:'productLine',
    key:'productLine'  
  },
  {
    title:'技能图标',
    dataIndex:'skillImg',
    key:'skillImg'  
  },
  {
    title:'技能描述',
    dataIndex:'des',
    key:'des'
  }
]

export const getTaskSkillListColumns = (renderObj) => {
  for (let key in renderObj) {
    taskSkillListColumns.forEach((item) => {
      if (item.key == key) {
        item.render = (text, record) => renderObj[key](text, record);
        item.renderFn = true;
      }
    });
  }
  return taskSkillListColumns;
};