<template>
    <v-container fluid class="login-container">
        <v-layout row wrap class="login-layout">
            <v-flex xl12 lg12 md12 sm12 xs12 class="login-title">
                <h1>Administrador</h1>
            </v-flex>
            <v-flex xl12 lg12 md12 sm12 xs12>
            <v-form ref="form" v-model="form" class="pa-3 pt-4">
                    <v-text-field
                        v-model="email"
                        :rules="[rules.email]"
                        box
                        color="white"
                        label="Email address"
                        type="email"
                    ></v-text-field>
                    <v-text-field
                        v-model="password"
                        :rules="[rules.password, rules.length(6)]"
                        box
                        color="white"
                        counter="6"
                        label="Password"
                        style="min-height: 96px"
                        type="password"
                    ></v-text-field>
                </v-form>
                <v-btn
                    
                    class="login-btn"
                    large
                    light
                >Ingresar</v-btn>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>

    export default {
        name: 'login',
        data() {
            return {
                user: undefined,
                password: undefined,
                rules: {
                    email: v => (v || '').match(/@/) || 'Please enter a valid email',
                    length: len => v => (v || '').length >= len || `Invalid character length, required ${len}`,
                    password: v => (v || '').match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(_|[^\w])).+$/) ||
                    'Password must contain an upper case letter, a numeric character, and a special character',
                    required: v => !!v || 'This field is required'
                }
            }
        },
        methods: {

        }
    }
</script>

<style scoped>
.login-container{
    width: 50%;
    margin: 10% auto;
    background-color: #252525;
}

.login-layout{
    /* background-color: white; */
}

.login-title{
    margin-top: 25px;
    text-align: center;
    color: white;
}

.login-button{
    color: white;
}

.login-button{
    padding-left: 50%;
}
</style>