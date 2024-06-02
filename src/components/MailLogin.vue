<template>
  <div class="container">
    <div class="mail-login-container">
      <h3 style="text-align: center">使用邮箱登录</h3>
      <div class="input-container">
        <input type="text" placeholder="请输入邮箱" v-model.lazy="userMail" class="input-field" />
      </div>
      <div class="input-container">
        <input type="text" placeholder="请输入邮箱验证码" v-model.lazy="mailCode" class="input-field" />
        <button @click="getMailCode" :disabled="isSendingCode" class="code-button">
          {{ isSendingCode ? `${remainingTime}s` : "发送验证码" }}
        </button>
      </div>
      <router-link to="UserRegister">注册</router-link>
      <router-link to="UserInfoLoginVue" style="float: right;">使用账号密码登录</router-link>
      <button @click="mailLogin" class="login-button">登录</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      userMail: "", // 用户邮箱
      mailCode: "", // 输入框内的邮箱验证码
      isSendingCode: false, // 2分钟内不能重复发送验证码
      remainingTime: 0, // 剩余时间
      timer: null, // 定时器
    };
  },
  methods: {
    async getMailCode() {
      if (this.isSendingCode) return;
      try {
        const resp = await axios.post("/api/mailTo", this.userMail);
        if (resp.data == "1") {
          this.isSendingCode = true;
          this.remainingTime = 120; // 设置初始剩余时间为 120 秒
          this.timer = setInterval(() => {
            if (this.remainingTime > 0) {
              this.remainingTime--;
            } else {
              clearInterval(this.timer);
              this.isSendingCode = false;
            }
          }, 1000); // 每秒更新剩余时间
        }else if(resp.data == "-2") {
          alert("120s内发送过验证码！")
          this.isSendingCode = true;
          this.remainingTime = 120; // 设置初始剩余时间为 120 秒
          this.timer = setInterval(() => {
            if (this.remainingTime > 0) {
              this.remainingTime--;
            } else {
              clearInterval(this.timer);
              this.isSendingCode = false;
            }
          }, 1000); // 每秒更新剩余时间
        }else if(resp.data == "-1"){
          alert("用户不存在！")
        }
      } catch (error) {
        console.error("发送验证码失败", error);
      }
    },
    async mailLogin() {
      const mailCodeInfo = {
        mail : this.userMail,
        code : this.mailCode
      }
      try {
        const resp = await axios.post("/api/mailLogin", mailCodeInfo);
        if (resp.data == "1") {
          alert("登录成功！");
        } else if(resp.data == "0") {
          alert("验证码错误！");
        }else if(resp.data == "-1") {
          alert("不存在该用户！");
        }
      } catch (error) {
        console.error("登录失败", error);
      }
    },
  },
  beforeUnmount() {
    clearInterval(this.timer); // 组件销毁前清除定时器
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

.input-container input[type="text"] {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 100%;
  height: 20px;
  /* 设置输入框的高度 */
}

.code-button {
  margin-left: 10px;
  padding: 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  height: 40px;
  /* 设置按钮的高度 */
  width: 86.65px;
}

.code-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.code-button:hover {
  background-color: #0056b3;
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
  margin-top: 5px;
}

.login-button:hover {
  background-color: #0056b3;
}
</style>