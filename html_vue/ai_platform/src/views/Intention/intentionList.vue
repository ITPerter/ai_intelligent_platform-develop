<template>
  <div id="intentionList-container">
      <el-card>
          <div class="header">
              <el-button type="primary">新增</el-button>
          </div>
          <div class="content">
                <el-empty description="意图列表为空，请添加意图" v-if="intentionList.length ===0"></el-empty>
                <el-table
                    v-else
                    :data="intentionList"
                    style="width: 100%">
                    <el-table-column
                        prop="name"
                        label="名称"
                        width="180">
                        <template slot-scope="scope">
                            <router-link :to="`/intentionDetail/${scope.row.index}`">{{scope.row.name}}</router-link>
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="code"
                        label="编码"
                        width="180">
                    </el-table-column>
                    <el-table-column
                        prop="createTime"
                        label="创建时间">
                    </el-table-column>
                    <el-table-column
                        prop="desc"
                        label="描述"
                        width="400">
                    </el-table-column>
                    <el-table-column
                        prop="operation"
                        label="操作"
                        >
                        <template>
                            <el-button type="text" class="edit">编辑</el-button>
                            <el-button type="text" class="del">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
          </div>
      </el-card>
  </div>
</template>

<script>
export default {
    data(){
        return{
            intentionList: [
                {
                    name: '2016-05-02',
                    code: '王小虎',
                    createTime: '上海市普陀区金沙江路 1518 弄',
                    desc:"TESTING"
                }, {
                    name: '2016-05-02',
                    code: '王小虎',
                    createTime: '上海市普陀区金沙江路 1518 弄',
                    desc:"TESTING"
                }, {
                    name: '2016-05-02',
                    code: '王小虎',
                    createTime: '上海市普陀区金沙江路 1518 弄',
                    desc:"TESTING"
                }, {
                    name: '2016-05-02',
                    code: '王小虎',
                    createTime: '上海市普陀区金沙江路 1518 弄',
                    desc:"TESTING"
                }
            ]
        }
    }
}
</script>

<style lang="less" scoped>
#intentionList-container{
    height: 100%;
    .el-card.is-always-shadow{
        height: 100%;
    }
    .header {
        border-bottom: 1px solid lightgray;
        text-align: left;
        padding: 5px 0;
    }

    .edit{
        color: black;
    }

    .del {
        color: red;
    }

    .content /deep/ .cell a{
        text-decoration: none;
        color:#66B1FF;
    }
}

</style>