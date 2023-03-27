<template>
  <div id="container">
    <el-card>
      <div id="header">
        <el-button type="primary">新增</el-button>
      </div>
      <div id="content">
          <el-table
            :data="tableData"
            stripe
            style="width: 100%"
            height="580">
            <el-table-column
              prop="name"
              label="名称"
              width="180">
            </el-table-column>
            <el-table-column
              prop="code"
              label="编码"
              width="180">
            </el-table-column>
            <el-table-column
              prop="desc"
              label="描述"
              width="700">
            </el-table-column>
            <el-table-column
              prop="operation"
              label="操作">
              <template>
                <el-button type="text" class="edit">编辑</el-button>
                <el-button type="text" class="del">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            layout="prev, pager, next"
            :total="50">
          </el-pagination>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [{
          name: '产品信息',
          code: 'SHANGNAIEN',
          desc: 'Testing'
        }, {
          name: '产品信息',
          code: 'SHANGNAIEN',
          desc: 'Testing'
        }, {
          name: '产品信息',
          code: 'SHANGNAIEN',
          desc: 'Testing'
        }, {
          name: '产品信息',
          code: 'SHANGNAIEN',
          desc: 'Testing'
        }, {
          name: '产品信息',
          code: 'SHANGNAIEN',
          desc: 'Testing'
        }, {
          name: '产品信息',
          code: 'SHANGNAIEN',
          desc: 'Testing'
        }, {
          name: '产品信息',
          code: 'SHANGNAIEN',
          desc: 'Testing'
        }, {
          name: '产品信息',
          code: 'SHANGNAIEN',
          desc: 'Testing'
        }, {
          name: '产品信息',
          code: 'SHANGNAIEN',
          desc: 'Testing'
        }, {
          name: '产品信息',
          code: 'SHANGNAIEN',
          desc: 'Testing'
        },{
          name: '产品信息',
          code: 'SHANGNAIEN',
          desc: 'Testing'
        },{
          name: '产品信息',
          code: 'SHANGNAIEN',
          desc: 'Testing'
        }]
    }
  },
  methods: {
 
  },
};
</script>

<style lang="less" scoped>
#container{
  height: 100%;
  .el-card.is-always-shadow{
    height: 100%;
  }

  #header {
    text-align: left;
    border-bottom: 1px solid lightgray;
    padding: 5px 0;
  }

  #content {
    .edit{
      color: black;
    }

    .del {
      color: red;
    }
  }

}

</style>>
