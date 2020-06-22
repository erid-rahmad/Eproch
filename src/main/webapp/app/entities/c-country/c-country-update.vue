<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.cCountry.home.createOrEditLabel" v-text="$t('opusWebApp.cCountry.home.createOrEditLabel')">Create or edit a CCountry</h2>
                <div>
                    <div class="form-group" v-if="cCountry.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cCountry.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cCountry.name')" for="c-country-name">Name</label>
                        <input type="text" class="form-control" name="name" id="c-country-name"
                            :class="{'valid': !$v.cCountry.name.$invalid, 'invalid': $v.cCountry.name.$invalid }" v-model="$v.cCountry.name.$model"  required/>
                        <div v-if="$v.cCountry.name.$anyDirty && $v.cCountry.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cCountry.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cCountry.code')" for="c-country-code">Code</label>
                        <input type="text" class="form-control" name="code" id="c-country-code"
                            :class="{'valid': !$v.cCountry.code.$invalid, 'invalid': $v.cCountry.code.$invalid }" v-model="$v.cCountry.code.$model"  required/>
                        <div v-if="$v.cCountry.code.$anyDirty && $v.cCountry.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cCountry.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.cCountry.code.pattern" v-text="$t('entity.validation.pattern', {pattern: 'Code'})">
                                This field should follow pattern for "Code".
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cCountry.withRegion')" for="c-country-withRegion">With Region</label>
                        <input type="checkbox" class="form-check" name="withRegion" id="c-country-withRegion"
                            :class="{'valid': !$v.cCountry.withRegion.$invalid, 'invalid': $v.cCountry.withRegion.$invalid }" v-model="$v.cCountry.withRegion.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cCountry.active')" for="c-country-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="c-country-active"
                            :class="{'valid': !$v.cCountry.active.$invalid, 'invalid': $v.cCountry.active.$invalid }" v-model="$v.cCountry.active.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cCountry.currency')" for="c-country-currency">Currency</label>
                        <select class="form-control" id="c-country-currency" name="currency" v-model="$v.cCountry.currencyId.$model" required>
                            <option v-if="!cCountry.currencyId" v-bind:value="null" selected></option>
                            <option v-bind:value="cCurrencyOption.id" v-for="cCurrencyOption in cCurrencies" :key="cCurrencyOption.id">{{cCurrencyOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.cCountry.currencyId.$anyDirty && $v.cCountry.currencyId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.cCountry.currencyId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cCountry.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./c-country-update.component.ts">
</script>
