const options = {
    componentRestrictions: {
        country: 'BR'
    },
    fields: ['geometry'],
    strictBounds: false

}

const input = document.querySelector('#form1\\:endereco');
const autocomplete = new google.maps.places.Autocomplete(input, options);