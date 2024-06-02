<template>
  <div class="container">
    <div class="mail-login-container">
      <h3 style="text-align: center">账号密码登录</h3>
      <div class="input-container">
        <input type="text" placeholder="请输入邮箱" v-model.lazy="userMail" class="input-field" />
      </div>
      <div class="input-container">
        <input type="password" placeholder="请输入密码" v-model.lazy="mailCode" class="input-field" />
      </div>
      <router-link to="UserRegister">注册</router-link>
      <router-link to="MailLogin" style="float: right;">使用邮箱验证码登录</router-link>
      <button @click="mailLogin" class="login-button">登录</button>
    </div>
  </div>
</template>

<script>
// import axios from "axios";
export default {
  data() {
    return {
      userMail: "", // 用户邮箱
      userPwd: "", // 用户密码
    };
  },
  methods: {

  },
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 90vh;
  /* 让容器铺满整个视口高度 */
}

.mail-login-container {
  max-width: 400px;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.input-container {
  margin-bottom: 10px;
  display: flex;
  align-items: center;
}

.input-container input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 100%;
  height: 20px;
  /* 设置输入框的高度 */
}


.login-button {
  display: block;
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  width: 280.65px;
  margin-top: 5px;
}

.login-button:hover {
  background-color: #0056b3;
}
</style>
