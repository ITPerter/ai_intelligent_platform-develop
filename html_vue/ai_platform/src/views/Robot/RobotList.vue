<template>
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
                <template slot-scope="scope">
                    <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
                    <el-button type="text" size="small">编辑</el-button>
                </template>
            </el-table-column>
        </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
    data(){
        return {
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
                }]
        }
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