require 'json'

class Bank 
    def initialize(key, name)
        @key = key
        @name = name
    end

    def key
        @key
    end

    def name
      @name
    end

    def as_json(options={})
        {
            key: @key,
            name: @name
        }
    end

    def to_json(*options)
        as_json(*options).to_json(*options)
    end

end

class BankHandler
    def initialize
      @banklist = [
        Bank.new("1", "SWEDBANK"),
        Bank.new("2", "IKANOBANKEN"),
        Bank.new("3", "JPMORGAN"),
        Bank.new("4", "NORDEA"),
        Bank.new("5", "CITIBANK"),
        Bank.new("6", "HANDELSBANKEN"),
        Bank.new("7", "SBAB"),
        Bank.new("8", "HSBC"),
        Bank.new("9", "NORDNET")
      ]
    end
    def list
      @banklist.to_json
    end
    def find_name(name)
      result = @banklist.find { |p| p.name == name}
        if result != nil
            return result.to_json
        else
            return "null"    
        end
    end
    def find_key(key)
        result = @banklist.find { |p| p.key == key}
          if result != nil
              return result.to_json
          else
              return "null"    
          end
      end
end

