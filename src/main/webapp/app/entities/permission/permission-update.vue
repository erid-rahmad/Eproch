<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.permission.home.createOrEditLabel" v-text="$t('opusWebApp.permission.home.createOrEditLabel')">Create or edit a Permission</h2>
                <div>
                    <div class="form-group" v-if="permission.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="permission.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.permission.name')" for="permission-name">Name</label>
                        <input type="text" class="form-control" name="name" id="permission-name"
                            :class="{'valid': !$v.permission.name.$invalid, 'invalid': $v.permission.name.$invalid }" v-model="$v.permission.name.$model"  required/>
                        <div v-if="$v.permission.name.$anyDirty && $v.permission.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.permission.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.permission.module')" for="permission-module">Module</label>
                        <input type="text" class="form-control" name="module" id="permission-module"
                            :class="{'valid': !$v.permission.module.$invalid, 'invalid': $v.permission.module.$invalid }" v-model="$v.permission.module.$model"  required/>
                        <div v-if="$v.permission.module.$anyDirty && $v.permission.module.$invalid">
                            <small class="form-text text-danger" v-if="!$v.permission.module.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.permission.canWrite')" for="permission-canWrite">Can Write</label>
                        <input type="checkbox" class="form-check" name="canWrite" id="permission-canWrite"
                            :class="{'valid': !$v.permission.canWrite.$invalid, 'invalid': $v.permission.canWrite.$invalid }" v-model="$v.permission.canWrite.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.permission.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./permission-update.component.ts">
</script>
