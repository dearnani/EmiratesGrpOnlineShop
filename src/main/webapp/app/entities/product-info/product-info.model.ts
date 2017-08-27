import { BaseEntity } from './../../shared';

const enum ProductType {
    'ELECTRONICS',
    'HOMEAPPLIANCES',
    'FURNITURE',
    'FABRICS'
}

const enum ProductStatus {
    'AVAILABLE',
    'NOT_AVAILABLE',
    'BLOCKED',
    'DELETED',
    'EXPIRED'
}

export class ProductInfo implements BaseEntity {
    constructor(
        public id?: number,
        public prouctCode?: string,
        public proudctName?: string,
        public productType?: ProductType,
        public productDescription?: string,
        public productStatus?: ProductStatus,
        public dealerProductInfos?: BaseEntity[],
        public productInfos?: BaseEntity[],
    ) {
    }
}
