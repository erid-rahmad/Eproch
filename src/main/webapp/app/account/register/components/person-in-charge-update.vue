<template>
    <el-form
        ref="person"
        label-position="right"
        label-width="128px"
        :model="person"
        :rules="rules"
        size="mini"
    >
        <el-form-item 
            :label="$t('register.pic.name')" 
            prop="name" 
            required
        >
            <el-input clearable v-model="person.name" />
        </el-form-item>
        <el-form-item
            v-if="contact"
            :label="$t('register.pic.username')"
            prop="userLogin"
            required 
        >
            <el-input clearable v-model="person.userLogin" />
        </el-form-item>
        <el-form-item 
            :label="$t('register.pic.position')" 
            prop="position" 
            required
        >
            <el-input clearable v-model="person.position" />
        </el-form-item>
        <el-form-item 
            :label="$t('register.pic.phone')" 
            prop="phone" 
            required
        >
            <el-input clearable v-model="person.phone" />
        </el-form-item>
        <el-form-item 
            :label="$t('register.pic.email')" 
            prop="email" 
            required
        >
            <el-input clearable v-model="person.email" />
        </el-form-item>
        <el-form-item
            v-if="contact"
            :label="$t('register.pic.subcategory')"
            prop="businessCategories"
            required
        >
            <el-select
                filterable
                style="width:100%"
                multiple
                v-model="person.businessCategories"
                :placeholder="$t('register.form.select')"
            >
                <el-option
                    v-for="item in businessCategories"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id + '_' + item.name"
                />
            </el-select>
        </el-form-item>
    </el-form>
</template>

<script lang="ts" src="./person-in-charge-update.component.ts"></script>
