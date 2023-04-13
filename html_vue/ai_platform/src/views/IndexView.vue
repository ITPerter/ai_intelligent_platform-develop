<template>
  <el-container>
    <el-aside  :width="isShowAside?'64px':'200px'">
      <div class="toggle-button" @click="toggleCollapse">| | |</div>
      <el-menu router :collapse="isShowAside" :collapse-transition="false">
        <el-submenu index="1">
          <template slot="title"><i class="el-icon-user-solid"></i>机器人</template>
            <el-menu-item index="/robotList">机器人列表</el-menu-item>
            <el-menu-item index="1-2">选项2</el-menu-item>
        </el-submenu>

        <el-submenu index="2">
          <template slot="title"><i class="el-icon-thumb"></i>意图</template>
            <el-menu-item index="/intetionList">意图列表</el-menu-item>
            <el-menu-item index="/intentionDetail/1">意图管理</el-menu-item>
        </el-submenu>
        <el-submenu index="3">
          <template slot="title"><i class="el-icon-s-data"></i>基础资料</template>
            <el-menu-item index="/baseData">基础资料</el-menu-item>
            <el-menu-item index="3-2">选项2</el-menu-item>
        </el-submenu>
        <el-submenu index="4">
          <template slot="title"><i class="el-icon-setting"></i>任务技能列表</template>
            <el-menu-item index="3-1">任务型技能详情</el-menu-item>
            <el-menu-item index="3-2">选项2</el-menu-item>
        </el-submenu>
        <el-submenu index="5">
          <template slot="title"><i class="el-icon-setting"></i>技能</template>
          <el-menu-item index="/taskSkills">任务技能列表</el-menu-item>
          <el-menu-item index="/questioningSkills">问答技能列表</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>Header</el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
// import { test } from "@/api/test"

export default {
  data(){
    return{
      isShowAside: false
    }
  },
  methods:{
    toggleCollapse(){
      this.isShowAside = !this.isShowAside
    }
  },
  // async created() {
  //   const  res = await test(1)
  //   console.log(res);
  // }
};
</script>

<style scoped>
/* ============================ 布局  ============================ */
.el-container {
  height: 100%;
}


/* ============================ 头部  ============================ */


.el-breadcrumb {
    /* font-size: 14px; */
    line-height: 4;
}

.el-header {
  position: relative;
  background-color: #fff;
  color: #333;
  height: 6vh !important;
  line-height: 6vh;

  transition: width 5s;
  border-bottom: 1px solid rgb(168, 168, 168);

}

.el-header>h4 {
    position: absolute;
    top: 0;
    right: 134px;
    width: 20%;
}


#CurrentUs{
  position: absolute;
  top: 0;
  right: 0;
  width: 11%;
  /* background-color: pink; */
  text-align: left;
  box-sizing: border-box;
  height: 50px;
  padding-top: 5px;
}

.el-dropdown {
  position: relative;
  top: -13px;
  margin: 0 15px;
}

.el-dropdown-link {
    cursor: pointer;
    /* color: #409EFF; */
  }
.el-icon-arrow-down {
  font-size: 12px;
}

.el-dialog{
  width: 30%;
  padding-right: 50px;
}

/* ============================侧边栏============================ */



.el-submenu .el-icon-message {
    margin-right: 10px;
}

.toggle-button {
    width: 100%;
    height: 50px;
    line-height: 50px;
    text-align: center;
    cursor: pointer;

    color: white;
}

.el-aside {
  background-color: rgb(30,36,52);
  color: #fff;
  text-align: center;
  line-height: 200px;

  transition: all 0.5s;
}

ul[class="el-menu"] {
    border: 0;
    transition: all 0.2s;
}

.el-menu {
    background-color: rgb(38,44,60);
    
}
.el-submenu .el-menu-item {
  text-align: left;
  background-color: rgb(50, 59, 82);
}

.el-submenu .el-menu-item,
.el-submenu /deep/ .el-submenu__title {
    color: white !important;
}

.el-submenu .el-menu-item:hover,
.el-submenu /deep/ .el-submenu__title:hover {
    background-color: rgb(78,113,135);
}

.el-submenu /deep/ .el-submenu__title {
  text-align: left;
}

.el-menu-item-group .el-menu-item-group__title {
  padding: 0;
}

.el-aside /deep/ i {
  margin-right: 15px;
}

/* ============================ 主体 ============================ */


.el-main {
  background-color: white;
  color: #333;
  text-align: center;
  padding: 8px;
}

/* 隐藏滚动条 */
 ::-webkit-scrollbar {
     width: 0 !important;
}
::-webkit-scrollbar {
    width: 0 !important;
    height: 0;
}
</style>