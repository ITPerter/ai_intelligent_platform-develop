

<template>
  <div>


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
                      <el-button type="text" @click="create1">添加意图</el-button>
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
                      <template slot-scope="scope">
                        <router-link :to="`/robotIntentDetails/`">{{scope.row.name}}</router-link>
                      </template>
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
                            <el-button type="text" size="small" @click="remove">删除</el-button>
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

<!--    嵌套表格-->
    <el-dialog title="收货地址" :visible.sync="dialogTableVisible">
      <el-table
          ref="multipleTable"
          :data="gridData"
          height="250"
          border
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="name"
            label="意图名称"
            width="120">
        </el-table-column>
        <el-table-column
            prop="no"
            label="意图编码"
            show-overflow-tooltip>
        </el-table-column>
      </el-table>
      <div style="margin-top: 20px">
        <el-button @click="toggleSelection()">取消选择</el-button>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogTableVisible = false">确 定</el-button>
      </div>
    </el-dialog>

  </el-card>
  </div>

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
            }],
          gridData: [{
            name: '转人工',
            no: '17788234111'
          }, {
            name: '查水位',
            no: '17788234111'
          }, {
            name: '出差申请',
            no: '17788234111'
          },{
            name: '语言接电话',
            no: '17788234111'
          }, {
            name: '回家申请',
            no: '17788234111'
          }],
          dialogTableVisible: false,
          dialogFormVisible: false,
          dialogFormVisible1: false,
          form: {
            name: '',
            encoding: '',
            date1: '',
            date2: '',
            delivery: false,
            type: [],
            resource: '',
            desc: ''
          },
          formLabelWidth: '120px',
        }
    },
  methods: {
    create() {
      this.dialogFormVisible = true
    },
    create1() {
      this.dialogTableVisible = true
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    remove() {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
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