<template>
    <v-container fluid class="login-container">
        <v-layout row wrap class="login-layout">
            <v-flex xl12 lg12 md12 sm12 xs12 class="login-title">
                <h1>Administrador</h1>
            </v-flex>
            <v-flex xl12 lg12 md12 sm12 xs12>
            <v-form  ref="form" v-model="valid" lazy-validation>
                    <v-text-field
                        v-model="email"
                        :rules="[rules.email]"
                        box
                        color="white"
                        label="Email address"
                        type="email"
                        required
                    ></v-text-field>
                    <v-text-field
                        v-model="password"
                        box
                        color="white"
                        counter="6"
                        label="Password"
                        style="min-height: 96px"
                        type="password"
                        required
                    ></v-text-field>
                <v-btn
                    class="login-btn"
                    light
                    @click="login"
                >Ingresar</v-btn>
            </v-form>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import axios from 'axios';
    // import router from 'vue-router';

    export default {
        name: 'login',
        data() {
            return {
                valid: true,
                email: '',
                password: '',
                rules: {
                    email: v => (v || '').match(/@/) || 'Please enter a valid email',
                    length: len => v => (v || '').length >= len || `Invalid character length, required ${len}`,
                    // password: v => (v || '').match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(_|[^\w])).+$/) ||
                    // 'Password must contain an upper case letter, a numeric character, and a special character',
                    required: v => !!v || 'This field is required'
                }
            }
        },
        methods: {
            submit(){
                let url = "http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/login/correo_admin/password_admin";

                if(this.$refs.form.validate()){
                    axios.post(url, {
                        correo_admin: this.email,
                        password_admin: this.password
                    }).then((response) => {console.log(response)})
                }
            },
            login(){
                if(this.email === 'admin@usach.cl' && this.password === 'tbd12018')
                {
                    console.log("email: "+this.email+" pass: "+this.password);
                    this.$router.push({path: '/admin'});
                }
            }

        }
    }
</script>

<style scoped>
.login-container{
    width: 50%;
    margin: 10% auto;
    background-color: #252525;
}

.login-title{
    margin-top: 25px;
    text-align: center;
    color: white;
}

.login-button{
    color: white;
}

.login-btn{
    /* margin-left: 40%; */
    /* padding-left: 50%; */
}
</style>