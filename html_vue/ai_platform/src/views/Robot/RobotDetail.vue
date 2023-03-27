<template>
  <el-card>
      <div class="btns-status">
          <el-card>
              <div class="btns">
                  <el-button type="primary">训练</el-button>
                  <el-button type="primary">对话测试</el-button>
              </div>
              <div class="status">
                <span>
                    <span class="title">机器人名称:</span>
                    互联网+演示
                </span>
                <span>
                    <span class="title">训练状态:</span>
                    已训练
                </span>
              </div>
          </el-card>
      </div>
      <div class="manage">
          <el-card>
              <div class="manage-header">
                  <div>意图管理</div>
                  <div>
                      <el-button type="text">添加意图</el-button>
                  </div>
              </div>
              <div class="manage-table">
                <el-table
                    height="420"
                    :data="tableData"
                    stripe
                    style="width: 100%">
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
                    prop="createTime"
                    label="创建时间"
                    width="200">
                    </el-table-column>
                    <el-table-column
                    prop="description"
                    label="描述">
                    </el-table-column>
                    <el-table-column
                    prop="operation"
                    label="操作"
                    width="90">
                        <template>
                            <el-button type="text" size="small">删除</el-button>
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
  </el-card>
</template>

<script>
export default {
    data(){
        return {
            tableData: [{
            name: '王小虎',
            code:"SHANGNAIEN",
            createTime: '2023-01-08',
            description:"Testing"
            }, {
            name: '王小虎',
            code:"SHANGNAIEN",
            createTime: '2023-01-08',
            description:"Testing"
            }, {
            name: '王小虎',
            code:"SHANGNAIEN",
            createTime: '2023-01-08',
            description:"Testing"
            }, {
            name: '王小虎',
            code:"SHANGNAIEN",
            createTime: '2023-01-08',
            description:"Testing"
            }, {
            name: '王小虎',
            code:"SHANGNAIEN",
            createTime: '2023-01-08',
            description:"Testing"
            }, {
            name: '王小虎',
            code:"SHANGNAIEN",
            createTime: '2023-01-08',
            description:"Testing"
            }, {
            name: '王小虎',
            code:"SHANGNAIEN",
            createTime: '2023-01-08',
            description:"Testing"
            }, {
            name: '王小虎',
            code:"SHANGNAIEN",
            createTime: '2023-01-08',
            description:"Testing"
            }, {
            name: '王小虎',
            code:"SHANGNAIEN",
            createTime: '2023-01-08',
            description:"Testing"
            }, {
            name: '王小虎',
            code:"SHANGNAIEN",
            createTime: '2023-01-08',
            description:"Testing"
            }]
        }
    }
}
</script>

<style lang="less" scoped>
.el-main>.el-card.is-always-shadow {
    height: 100%;
}

.btns-status {
    height: 100px;
    .el-card.is-always-shadow {
        height: 100%;
    }
    & /deep/ .el-card__body {
        text-align: left;
    }
    .status {
        margin-top: 10px;
        &>span{
            font-size: 14px;
        }
        .title {
            font-weight: bold;
            font-size: 16px;
        }
        &>span:nth-child(2){
            margin-left: 20px;
        }
    }
}
.manage {
    position: relative;
    height: 520px;
    margin-top: 20px;
    .el-card.is-always-shadow {
        height: 100%;
    }
    .manage-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        height: 50px;
        border-bottom: 1px solid lightgray;
    }
    & /deep/ .el-card__body {
        padding: 0 20px;
    }
    .el-pagination {
        position: absolute;
        bottom: 0;
    }
}

</style>