<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="permission">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.permission.detail.title')">Permission</span> {{permission.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.permission.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{permission.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.permission.module')">Module</span>
                    </dt>
                    <dd>
                        <span>{{permission.module}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.permission.canWrite')">Can Write</span>
                    </dt>
                    <dd>
                        <span>{{permission.canWrite}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="permission.id" :to="{name: 'PermissionEdit', params: {permissionId: permission.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./permission-details.component.ts">
</script>
