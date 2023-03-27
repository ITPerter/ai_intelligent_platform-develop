<template>
  <div id="intentionDetail-container">
    <div class="word-slot-manage">
        <el-card>
            <div class="common-header">
                <div>词槽管理</div>
                <div><el-button type="text">新增</el-button></div>
            </div>
            <div class="slot-content">
                <el-table
                    :data="wordSlotData"
                    height="300"
                    border
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
                    prop="term"
                    label="澄清术语"
                    width="280">
                    </el-table-column>
                    <el-table-column
                    prop="record"
                    label="是否必录">
                    </el-table-column>
                    <el-table-column
                    prop="type"
                    label="类型">
                    </el-table-column>
                    <el-table-column
                    prop="desc"
                    label="描述">
                    </el-table-column>
                    <el-table-column
                    prop="op"
                    label="操作">
                        <template>
                            <el-button type="text" class="edit">编辑</el-button>
                            <el-button type="text" class="del">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </el-card>
    </div>
    <div class="example-manage">
        <el-card>
            <div class="common-header">
                <div>样本管理</div>
                <div><el-button type="text">新增</el-button></div>
            </div>
            <div class="example-content">
                <el-table
                :data="exampleData"
                style="width: 100%"
                height="300">
                    <el-table-column
                        prop="examples"
                        label="样本标注"
                        width="980">
                        <template>
                            <el-tag
                                v-for="item in items"
                                :key="item.label"
                                :type="item.type"
                                effect="plain">
                                {{ item.label }}
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="status"
                        label="状态"
                        width="180">
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
            </div>
        </el-card>
    </div>
  </div>
</template>

<script>
export default {
    data(){
        return {
            // 词槽数据
            wordSlotData: [{
            name: '王小虎',
            code:"SHANGNAIEN",
            term: '上海市普陀区金沙江路 1518 弄',
            record:"是",
            type:"文本",
            desc:"Testing"
            }, {
            name: '王小虎',
            code:"SHANGNAIEN",
            term: '上海市普陀区金沙江路 1518 弄',
            record:"是",
            type:"文本",
            desc:"Testing"
            }, {
            name: '王小虎',
            code:"SHANGNAIEN",
            term: '上海市普陀区金沙江路 1518 弄',
            record:"是",
            type:"文本",
            desc:"Testing"
            }, {
            name: '王小虎',
            code:"SHANGNAIEN",
            term: '上海市普陀区金沙江路 1518 弄',
            record:"是",
            type:"文本",
            desc:"Testing"
            }, {
            name: '王小虎',
            code:"SHANGNAIEN",
            term: '上海市普陀区金沙江路 1518 弄',
            record:"是",
            type:"文本",
            desc:"Testing"
            }, {
            name: '王小虎',
            code:"SHANGNAIEN",
            term: '上海市普陀区金沙江路 1518 弄',
            record:"是",
            type:"文本",
            desc:"Testing"
            }, {
            name: '王小虎',
            code:"SHANGNAIEN",
            term: '上海市普陀区金沙江路 1518 弄',
            record:"是",
            type:"文本",
            desc:"Testing"
            }],
            // 样本管理
            exampleData: [{
                status: '已训练'
            }, {
                status: '已训练'
            }, {
                status: '已训练'
            }, {
                status: '已训练'
            }],
            items: [
                { type: '', label: 'SHANG' },
                { type: 'info', label: 'SHANG' },
                { type: '', label: 'SHANG' },
                { type: 'info', label: 'SHANG' },
            ]
        }
    }
}
</script>

<style lang="less" scoped>
#intentionDetail-container {
    height: 120%;
    .word-slot-manage {
        height: 400px;
        margin-bottom: 20px;
    }
}

.common-header {
    display: flex;
    justify-content: space-between;
    border-bottom: 1px solid lightgray;
}

.el-tag.el-tag--plain {
    margin-right: 5px;
}

.edit{
    color: black;
}

.del {
    color: red !important;
}

</style>