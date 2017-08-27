import { BaseEntity } from './../../shared';

export class CustomerInfo implements BaseEntity {
    constructor(
        public id?: number,
        public aliasName?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phoneNumber?: string,
        public lastLogin?: any,
        public location?: BaseEntity,
        public customerInfos?: BaseEntity[],
    ) {
    }
}
