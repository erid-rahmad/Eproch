<template>
    <el-form
        ref="person"
        label-position="left"
        label-width="128px"
        :model="person"
        :rules="rules"
    >
        <el-form-item label="Name" prop="name" required>
            <el-input v-model="person.name" />
        </el-form-item>
        <el-form-item
            label="Username"
            prop="username"
            required v-if="contact"
        >
            <el-input v-model="person.username" />
        </el-form-item>
        <el-form-item label="Position" prop="position" required>
            <el-input v-model="person.position" />
        </el-form-item>
        <el-form-item label="Phone No." prop="phone" required>
            <el-input v-model="person.phone" />
        </el-form-item>
        <el-form-item label="Email Address" prop="email" required>
            <el-input v-model="person.email" />
        </el-form-item>
        <el-form-item
            label="Division"
            prop="businessCategories"
            required
            v-if="contact"
        >
            <el-select
                filterable
                multiple
                v-model="person.businessCategories"
                placeholder="Select Division"
            >
                <el-option
                    v-for="item in businessCategories"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                />
            </el-select>
        </el-form-item>
    </el-form>
</template>

<script lang="ts" src="./person-in-charge-update.component.ts"></script>
