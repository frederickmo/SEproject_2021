<template>
  <div style="z-index: -1" class="waveWrapper waveAnimation">
    <div class="waveWrapperInner bgTop">
      <div
        class="wave waveTop"
        style="
          background-image: url('http://front-end-noobs.com/jecko/img/wave-top.png');
        "
      ></div>
    </div>
    <div class="waveWrapperInner bgMiddle">
      <div
        class="wave waveMiddle"
        style="
          background-image: url('http://front-end-noobs.com/jecko/img/wave-mid.png');
        "
      ></div>
    </div>
    <div class="waveWrapperInner bgBottom">
      <div
        class="wave waveBottom"
        style="
          background-image: url('http://front-end-noobs.com/jecko/img/wave-bot.png');
        "
      ></div>
    </div>
  </div>

  <div style="position: absolute; top: 9%; left: 30%; right: 30%">
    <img src="./../../assets/onetake-logo-white-ver.png" style="width: 450px" />
  </div>
  <div class="slogan">
    <div style="font-size: 40px">实验教学管理系统</div>
    <div style="font-size: 22px">
      ONE-TAKE! Experiment Teaching Management System
    </div>
  </div>

  <div style="z-index: 1">
    <va-card class="auth-box">
      <va-card-content>
        <va-tabs style="font-size: 16px" center v-model="tabIndex">
          <template #tabs>
            <va-tab name="Login">登录</va-tab>
            <va-tab name="Signup">注册</va-tab>
          </template>
        </va-tabs>
        <va-separator />
        <div>
          <router-view />
        </div>
      </va-card-content>
    </va-card>
  </div>
</template>

<script>
// import Login from './Login.vue'
// import Signup from './Signup.vue'

export default {
  name: "Auth",
  components: {
    // Login,
    // Signup,
  },
  data() {
    return {
      tabValue: 0,
      activeName: "",
      // valueStr: 0
    };
  },
  computed: {
    tabIndex: {
      set(tabName) {
        this.$router.push({ name: tabName });
      },
      get() {
        return this.$route.name;
      },
    },
  },
  mounted() {
    // console.log("tabValue transferred here: ", this.$route.params.tabValue);
    this.$router.push({ name: "Login" });
    this.activeName = "login";
  },
  methods: {
    // handleClick(tab, event) {
    //   console.log(tab, event);
    //   console.log("activeName: ", this.activeName);
    //   this.$router.push({ name: this.activeName });
    // },
  },
};
</script>

<style>
html {
  width: 100%;
}

body {
  width: 100%;
  /* background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
	background-size: 400% 400%;
	animation: gradient 15s ease infinite;
	height: 100vh; */
}

/* @keyframes gradient {
	0% {
		background-position: 0% 50%;
	}
	50% {
		background-position: 100% 50%;
	}
	100% {
		background-position: 0% 50%;
	}
} */

.slogan {
  position: absolute;
  top: 28%;
  left: 30%;
  right: 30%;
  min-width: 400px;
  font-weight: bold;
  color: white;
}

@keyframes move_wave {
  0% {
    transform: translateX(0) translateZ(0) scaleY(1);
  }
  50% {
    transform: translateX(-25%) translateZ(0) scaleY(0.55);
  }
  100% {
    transform: translateX(-50%) translateZ(0) scaleY(1);
  }
}
.waveWrapper {
  overflow: hidden;
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  top: 0;
  margin: auto;
}
.waveWrapperInner {
  position: absolute;
  width: 100%;
  overflow: hidden;
  height: 100%;
  bottom: -1px;
  background-image: linear-gradient(to top, #75adee 20%, #002b88 80%);
}
.bgTop {
  z-index: 15;
  opacity: 0.5;
}
.bgMiddle {
  z-index: 10;
  opacity: 0.75;
}
.bgBottom {
  z-index: 5;
}
.wave {
  position: absolute;
  left: 0;
  width: 200%;
  height: 100%;
  background-repeat: repeat no-repeat;
  background-position: 0 bottom;
  transform-origin: center bottom;
}
.waveTop {
  background-size: 50% 100px;
}
.waveAnimation .waveTop {
  animation: move-wave 3s;
  -webkit-animation: move-wave 3s;
  -webkit-animation-delay: 1s;
  animation-delay: 1s;
}
.waveMiddle {
  background-size: 50% 120px;
}
.waveAnimation .waveMiddle {
  animation: move_wave 10s linear infinite;
}
.waveBottom {
  background-size: 50% 100px;
}
.waveAnimation .waveBottom {
  animation: move_wave 15s linear infinite;
}

.auth-box {
  width: 35%;
  height: 35%;
  min-height: 240px;
  position: fixed;
  margin: auto;
  left: 0;
  right: 0;
  top: 39%;
}
</style>
