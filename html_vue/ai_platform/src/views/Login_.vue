<template>
  <div id="login-container">
    <el-form :model="userForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item prop="pass">
        <el-input type="password" v-model="userForm.account" autocomplete="off" placeholder="Please input your account">
          <i slot="prefix" class="el-icon-user-solid"></i>
        </el-input>
      </el-form-item>
      <el-form-item prop="checkPass">
        <el-input type="password" v-model="userForm.password" autocomplete="off" placeholder="Please input your password">
          <i slot="prefix" class="el-icon-s-tools"></i>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data(){
    return {
      userForm: {
          account: '',
          password: ''
        }
    }
  }
}
</script>

<style lang="less" scoped>
#login-container{
  width: 28%;
  height: 35%;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  padding: 35px 0;
  box-sizing: border-box;
  border: 1px solid lightgray;
  border-radius: 20px;
  box-shadow: 0px 0px 10px white;

  & /deep/ .el-form-item__content{
    margin: 0 70px !important;
  }
}
</style>>
