
<!--机器人列表-->
<template>
  <div>
    <el-card>
    <div class="headerLeft">
      <h1 class="htitle">任务技能列表</h1>
      <div style="width: 400px"><el-row class="button1">
        <el-button type="primary" @click="create">新增</el-button>
        <el-button type="danger" @click="remove">删除</el-button>
        <el-button type="success" @click="getRobotList">刷新</el-button>
        <el-button type="warning">退出</el-button>

      </el-row>
      </div>
      <div style="width: 400px; float: right">
        <el-input
            placeholder="搜索技能名称"
            v-model="input"
            clearable>
        </el-input>
        <el-dropdown>
              <span class="el-dropdown-link">
                  展开过滤<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>黄金糕</el-dropdown-item>
            <el-dropdown-item>狮子头</el-dropdown-item>
            <el-dropdown-item>螺蛳粉</el-dropdown-item>
            <el-dropdown-item disabled>双皮奶</el-dropdown-item>
            <el-dropdown-item divided>蚵仔煎</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    </el-card>
  <div id="robotList-container">
    <el-card>
        <el-table :data="tableData" height="650" border style="width: 100%">
            <el-table-column prop="robotName" label="名称" width="180">
                <template slot-scope="scope">
                    <router-link :to="`/robotDetail/${scope.row.index}`">{{scope.row.robotName}}</router-link>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
            <el-table-column prop="desc" label="描述"></el-table-column>
            <el-table-column prop="creator" label="创建人"></el-table-column>
            <el-table-column prop="status" label="训练状态"></el-table-column>
            <el-table-column prop="operation" label="操作">
                <template>
                  <el-button type="text" size="small" @click="edit">编辑</el-button>
                    <el-button @click="remove1" type="text" size="small" >删除</el-button>
                </template>
            </el-table-column>
        </el-table>
    </el-card>
  </div>

    <el-dialog title="新建机器人" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="编码" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="编辑机器人" :visible.sync="dialogFormVisible1">
      <el-form :model="form">
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="编码" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible1 = false">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getRobotList } from '@/api/test'
export default {
    data(){
        return {
          page: {
            number: 1,
            size: 5
          },
             tableData: [{
                robotName: '2016-05-03',
                createTime: '王小虎',
                desc: '上海市普陀区金沙江路 1518 弄',
                creator:'商乃恩',
                status:"已训练",
                }, {
                robotName: '2016-05-03',
                createTime: '王小虎',
                desc: '上海市普陀区金沙江路 1518 弄',
                creator:'商乃恩',
                status:"已训练",
                }, {
                robotName: '2016-05-03',
                createTime: '王小虎',
                desc: '上海市普陀区金沙江路 1518 弄',
                creator:'商乃恩',
                status:"已训练",
                }, {
                robotName: '2016-05-03',
                createTime: '王小虎',
                desc: '上海市普陀区金沙江路 1518 弄',
                creator:'商乃恩',
                status:"已训练",
                }, {
                robotName: '2016-05-03',
                createTime: '王小虎',
                desc: '上海市普陀区金沙江路 1518 弄',
                creator:'商乃恩',
                status:"已训练",
                }, {
                robotName: '2016-05-03',
                createTime: '王小虎',
                desc: '上海市普陀区金沙江路 1518 弄',
                creator:'商乃恩',
                status:"已训练",
                }, {
                robotName: '2016-05-03',
                createTime: '王小虎',
                desc: '上海市普陀区金沙江路 1518 弄',
                creator:'商乃恩',
                status:"已训练",
                }],
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
        }
    },

  methods :{
      getRobotList() {
        getRobotList(this.page).then(response => {
          console.log("-------------------------")
          console.log(response.data);
          console.log("--------------------------")
        })
      },
    create() {
      this.dialogFormVisible = true
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

    remove1() {
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
    edit() {
      this.dialogFormVisible1 = true
    },
  }
}
</script>

<style lang="less" scoped>
#robotList-container {
    & /deep/ .cell a{
        text-decoration: none;
        color:#66B1FF;
    }
}
</style>